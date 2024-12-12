package modell.quarkus.contract.resource.abstrc;


import io.github.agache41.annotator.accessor.PositionComparator;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.HaveAnnotation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import modell.quarkus.contract.classes.*;
import modell.quarkus.contract.interfaces.HeaderInfo;
import modell.quarkus.contract.interfaces.IFilterModel;
import modell.quarkus.contract.interfaces.ISortModelItem;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public abstract class AbstractAGGridDataAccess<ENTITY> {

    protected final DateTimeFormatter dateParser = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
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

    protected final Class<ENTITY> entityClass;
    protected final List<ColDef<ENTITY, ?>> header;

    public AbstractAGGridDataAccess(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        this.header = Annotator
                .of(this.entityClass)
                .getAccessorsThat(HaveAnnotation.ofType(HeaderInfo.class))
                .sorted(new PositionComparator())
                .map(accessor -> new ColDef<ENTITY, Object>(accessor.getName(), accessor.getAnnotation(HeaderInfo.class, true)))
                .collect(Collectors.toList());
    }

    public abstract EntityManager em();

    public List<ColDef<ENTITY, ?>> getHeader() {
        return this.header;
    }

    public EnterpriseGetRowsResponse data(ServerSideGetRowsRequest request) {
        final int startRow = request.getStartRow() != null ? request.getStartRow() : MIN_ROW;
        final int endRow = request.getEndRow() != null ? request.getEndRow() : MAX_ROW;
        final int maxResults = Math.abs(endRow - startRow);
        final List<ENTITY> data = this.find(startRow, maxResults, request.getSortModel(), request.getFilterModel());
        final Long lastRow = this.count(request.getFilterModel());
        final EnterpriseGetRowsResponse<ENTITY> response = new EnterpriseGetRowsResponse<>();
        response.setData(data);
        response.setLastRow(lastRow);
        return response;
    }

    public List<ENTITY> find(
            int firstResult,
            int maxResults,
            List<ISortModelItem> sortModels,
            Map<String, IFilterModel> filterModel,
            Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<ENTITY> query = cb.createQuery(entityClass);
        Root<ENTITY> root = query.from(entityClass);
        Order[] orders = getOrders(cb, root, sortModels, parameters);
        Predicate[] predicates = getPredicates(cb, root, filterModel, parameters);
        return em()
                .createQuery(query.select(root)
                                  .orderBy(orders)
                                  .where(predicates))
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }


    public Long count(Map<String, IFilterModel> filterModel, Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<ENTITY> root = query.from(entityClass);
        Predicate[] predicates = getPredicates(cb, root, filterModel, parameters);
        return em().createQuery(query.select(cb.count(root))
                                     .where(predicates))
                   .getSingleResult();
    }

    protected Order getOrder(CriteriaBuilder cb, Root<ENTITY> root, ISortModelItem sortModel, Object[] parameters) {
        if (ASCENDING.contains(sortModel.getSort())) {
            return cb.asc(root.get(sortModel.getColId()));
        }
        if (DESCENDING.contains(sortModel.getSort())) {
            return cb.desc(root.get(sortModel.getColId()));
        }
        throw new IllegalArgumentException("No such ordering:" + sortModel.getSort());
    }

    /**
     * Default sorting is by id asc
     *
     * @return
     */
    protected SortModel getDefaultSortModel() {
        return new SortModel("id", "asc");
    }

    private final Order[] getOrders(
            final CriteriaBuilder cb,
            final Root<ENTITY> root,
            List<ISortModelItem> sortModels,
            Object[] parameters) {

        // todo: clarify and implement
        // if (sortModels == null || sortModels.isEmpty())
        //    return getDefaultSortModel();

        return sortModels.stream()
                         .map(sortModel -> getOrder(cb, root, sortModel, parameters))
                         .filter(Objects::nonNull)
                         .toArray(Order[]::new);
    }

    private final Predicate[] getPredicates(
            CriteriaBuilder cb,
            Root<ENTITY> root,
            Map<String, IFilterModel> filterModel,
            Object[] parameters) {
        return filterModel.entrySet()
                          .stream()
                          .map(entry -> getPredicate(cb, root, entry.getKey(), entry.getValue(), parameters))
                          .filter(Objects::nonNull)
                          .toArray(Predicate[]::new);
    }

    // to implement the various cases for fe filters
    private Predicate getPredicate(
            CriteriaBuilder cb,
            Root<ENTITY> root,
            String field,
            IFilterModel columnfilter,
            Object[] parameters) {

        //todo: optimize mit enum based
        if (TEXT.equals(columnfilter.getFilterType())) {
            final Expression<String> value = cb.lower(root.get(field)
                                                          .as(String.class));
            final String filter = columnfilter
                    .getFilter()
                    .toLowerCase(Locale.ROOT);
            final String filterLike = filter
                    .replaceAll("\\s+", "%");
            if (CONTAINS.equals(columnfilter.getType())) {
                return cb.like(value, "%" + filterLike + "%");
            }
            if (NOT_CONTAINS.equals(columnfilter.getType())) {
                return cb.notLike(value, "%" + filterLike + "%");
            }
            if (STARTS_WITH.equals(columnfilter.getType())) {
                return cb.like(value, filterLike + "%");
            }
            if (ENDS_WITH.equals(columnfilter.getType())) {
                return cb.like(value, "%" + filterLike);
            }
            if (EQUALS.equals(columnfilter.getType())) {
                return cb.equal(value, filter);
            }
            if (NOT_EQUAL.equals(columnfilter.getType())) {
                return cb.notEqual(value, filter);
            }
        }
        if (DATE.equals(columnfilter.getFilterType())) {
            final Expression<OffsetDateTime> value = root.get(field)
                                                         .as(OffsetDateTime.class);
            if (EQUALS.equals(columnfilter.getType())) {
                return cb.equal(value, OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser));
            }
            if (GREATER_THAN.equals(columnfilter.getType())) {
                return cb.greaterThanOrEqualTo(value, OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser));
            }
            if (LESS_THAN.equals(columnfilter.getType())) {
                return cb.lessThanOrEqualTo(value, OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser));
            }
            if (NOT_EQUAL.equals(columnfilter.getType())) {
                return cb.notEqual(value, OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser));
            }
            if (IN_RANGE.equals(columnfilter.getType())) {
                return cb
                        .between(
                                value,
                                OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser),
                                OffsetDateTime.parse(columnfilter.getDateTo(), dateParser));
            }
        }
        return null;
    }
}
