package modell.quarkus.grid.contract.resource.abstrc;


import io.github.agache41.annotator.accessor.PositionComparator;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.HaveAnnotation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import modell.quarkus.grid.contract.classes.header.ColDef;
import modell.quarkus.grid.contract.classes.header.annotation.HeaderInfo;
import modell.quarkus.grid.contract.classes.request.CombinedSimpleModel;
import modell.quarkus.grid.contract.classes.request.GetRowsParam;
import modell.quarkus.grid.contract.classes.request.filter.Operator;
import modell.quarkus.grid.contract.classes.request.sort.SortModel;
import modell.quarkus.grid.contract.classes.response.Rows;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public abstract class AbstractAGGridDataAccess<ENTITY> {

    protected final DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                    .withZone(ZoneId.of("+02:00"));

    protected static final Integer MIN_ROW = 0;
    protected static final Integer MAX_ROW = 10000;

    protected static final Set<String> ASCENDING = new TreeSet(Arrays.asList("asc", "ASC", "ascending"));
    protected static final Set<String> DESCENDING = new TreeSet(Arrays.asList("desc", "DESC", "descending"));

    protected static final String TEXT = "text";
    protected static final String CONTAINS = "contains";
    protected static final String NOT_CONTAINS = "notContains";
    protected static final String STARTS_WITH = "startsWith";
    protected static final String ENDS_WITH = "endsWith";
    protected static final String EQUALS = "equals";
    protected static final String NOT_EQUAL = "notEqual";
    protected static final String DATE = "date";
    protected static final String GREATER_THAN = "greaterThan";
    protected static final String LESS_THAN = "lessThan";
    protected static final String IN_RANGE = "inRange";
    protected static final Predicate[] NO_FILTER = new Predicate[0];
    protected static final Order[] NO_ORDER = new Order[0];

    protected final Class<ENTITY> entityClass;
    protected final List<ColDef<ENTITY, ?>> header;

    public AbstractAGGridDataAccess(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        final HeaderInfo entityClassAnnotation = entityClass.getAnnotation(HeaderInfo.class);
        this.header = Annotator.of(this.entityClass)
                               .getAccessorsThat(HaveAnnotation.ofType(HeaderInfo.class))
                               .sorted(new PositionComparator())
                               .map(accessor -> new ColDef<ENTITY, Object>(accessor.getName(), entityClassAnnotation, accessor.getAnnotation(HeaderInfo.class, true)))
                               .collect(Collectors.toList());
    }

    public abstract EntityManager em();

    public List<ColDef<ENTITY, ?>> getHeader() {
        return this.header;
    }

    public Rows data(GetRowsParam request) {
        final int startRow = request.getStartRow() != null ? request.getStartRow() : MIN_ROW;
        final int endRow = request.getEndRow() != null ? request.getEndRow() : MAX_ROW;
        final List<ENTITY> data = this.find(startRow, Math.abs(endRow - startRow), request.getSortModel(), request.getFilterModel());
        return new Rows<>(data, startRow + data.size(), null);
    }

    public List<ENTITY> find(int firstResult, int maxResults, List<SortModel> sortModels, Map<String, CombinedSimpleModel> filterModel, Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<ENTITY> query = cb.createQuery(entityClass);
        Root<ENTITY> root = query.from(entityClass);
        Order[] orders = getOrders(cb, root, sortModels, parameters);
        Predicate[] predicates = getPredicates(cb, root, filterModel, parameters);
        return em().createQuery(query.select(root)
                                     .orderBy(orders)
                                     .where(predicates))
                   .setFirstResult(firstResult)
                   .setMaxResults(maxResults)
                   .getResultList();
    }


    public Long count(Map<String, CombinedSimpleModel> filterModel, Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<ENTITY> root = query.from(entityClass);
        Predicate[] predicates = getPredicates(cb, root, filterModel, parameters);
        return em().createQuery(query.select(cb.count(root))
                                     .where(predicates))
                   .getSingleResult();
    }

    private Order[] getOrders(final CriteriaBuilder cb, final Root<ENTITY> root, List<SortModel> sortModels, Object[] parameters) {
        if (sortModels == null || sortModels.isEmpty()) return NO_ORDER;
        return sortModels.stream()
                         .map(sortModel -> sortModel.getSort()
                                                    .function()
                                                    .apply(cb, root.get(sortModel.getColId())))
                         .filter(Objects::nonNull)
                         .toArray(Order[]::new);
    }

    private Predicate[] getPredicates(CriteriaBuilder cb, Root<ENTITY> root, Map<String, CombinedSimpleModel> filterModel, Object[] parameters) {
        if (filterModel == null || filterModel.isEmpty()) return NO_FILTER;
        return filterModel.entrySet()
                          .stream()
                          .map(entry -> getPredicate(cb, root, entry.getKey(), entry.getValue(), parameters))
                          .filter(Objects::nonNull)
                          .toArray(Predicate[]::new);
    }

    private Predicate getPredicate(CriteriaBuilder cb, Root<ENTITY> root, String field, CombinedSimpleModel columnfilter, Object[] parameters) {
        final Operator operator = columnfilter.getOperator();
        final List<CombinedSimpleModel> conditions = columnfilter.getConditions();
        if (operator == null || conditions == null || conditions.isEmpty()) // simple case
            return getOnePredicate(cb, root, field, columnfilter, parameters);
        else {
            //extract each predicate
            final Predicate[] predicates = conditions.stream()
                                                     .map(combinedSimpleModel -> getOnePredicate(cb, root, field, combinedSimpleModel, parameters))
                                                     .toArray(Predicate[]::new);
            //combine them
            return operator.function()
                           .apply(cb, predicates);
        }
    }

    @SuppressWarnings("unchecked")
    private <FIELD extends Comparable<FIELD>> Predicate getOnePredicate(CriteriaBuilder cb, Root<ENTITY> root, String field, CombinedSimpleModel columnfilter, Object[] parameters) {
        final Class<FIELD> fieldType = (Class<FIELD>) getFieldType(this.entityClass, field);
        final Expression<FIELD> value = root.get(field)
                                            .as(fieldType);
        return columnfilter.getFilterType()
                           .criteriaFilter()
                           .predicate(cb, root, value, fieldType, columnfilter, parameters);
    }
    private Class<?> getFieldType(Class<ENTITY> entityClass, String field) {
        try {
            return this.entityClass.getField(field)
                                   .getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Error filtering class " + entityClass.getSimpleName(), e);
        }
    }
}
