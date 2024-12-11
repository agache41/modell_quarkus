package modell.quarkus.contract.resource;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import modell.quarkus.contract.classes.ColumnFilter;
import modell.quarkus.contract.classes.SortModel;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public abstract class SSRMdaoProvider<T> {

    static final DateTimeFormatter dateParser = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("+02:00"));
    private static final Set<String> ASCENDING = new TreeSet(Arrays.asList("asc", "ASC", "ascending"));
    private static final Set<String> DESCENDING = new TreeSet(Arrays.asList("desc", "DESC", "descending"));

    private static final String TEXT = "text";
    private static final String CONTAINS = "contains";
    private static final String NOT_CONTAINS = "notContains";
    private static final String STARTS_WITH = "startsWith";
    private static final String ENDS_WITH = "endsWith";
    private static final String EQUALS = "equals";
    private static final String NOT_EQUAL = "notEqual";
    private static final String DATE = "date";
    private static final String GREATER_THAN = "greaterThan";
    private static final String LESS_THAN = "lessThan";
    private static final String IN_RANGE = "inRange";

    protected final Class<T> type;

    public SSRMdaoProvider(Class<T> type) {
        this.type = type;
    }

    public abstract EntityManager em();

    public List<T> find(
            int firstResult,
            int maxResults,
            List<SortModel> sortModels,
            Map<String, ColumnFilter> filterModel,
            Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> root = query.from(type);
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


    public Long count(Map<String, ColumnFilter> filterModel, Object... parameters) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(type);
        Predicate[] predicates = getPredicates(cb, root, filterModel, parameters);
        return em().createQuery(query.select(cb.count(root))
                                     .where(predicates))
                   .getSingleResult();
    }

    protected Order getOrder(CriteriaBuilder cb, Root<T> root, SortModel sortModel, Object[] parameters) {
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
            final Root<T> root,
            List<SortModel> sortModels,
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
            Root<T> root,
            Map<String, ColumnFilter> filterModel,
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
            Root<T> root,
            String field,
            ColumnFilter columnfilter,
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
