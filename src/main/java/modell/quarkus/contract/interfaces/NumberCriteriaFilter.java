package modell.quarkus.contract.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.contract.classes.CombinedSimpleModel;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.Map;

public class NumberCriteriaFilter implements ICriteriaFilter {


    protected static final String GREATER_THEN = "greaterThan";
    protected static final String GREATER_THEN_OR_EQUAL = "greaterThanOrEqual";
    protected static final String LESS_THEN = "lessThan";
    protected static final String LESS_THEN_OR_EQUAL = "lessThanOrEqual";
    protected static final String IN_RANGE = "inRange";

    protected static final String EQUALS = "equals";
    protected static final String NOT_EQUAL = "notEqual";


    protected static final Map<String, TriFunction<CriteriaBuilder, Expression<Long>, Long, Predicate>> predicateMap = new HashMap<>();

    static {
        predicateMap.put(GREATER_THEN, CriteriaBuilder::greaterThan);
        predicateMap.put(GREATER_THEN_OR_EQUAL, CriteriaBuilder::greaterThanOrEqualTo);
        predicateMap.put(LESS_THEN, CriteriaBuilder::lessThan);
        predicateMap.put(LESS_THEN_OR_EQUAL, CriteriaBuilder::lessThanOrEqualTo);
        predicateMap.put(EQUALS, CriteriaBuilder::equal);
        predicateMap.put(NOT_EQUAL, CriteriaBuilder::notEqual);
    }

    @Override
    public <ENTITY> Predicate predicate(CriteriaBuilder cb, Root<ENTITY> root, String field, CombinedSimpleModel columnfilter, Object[] parameters) {
        final Expression<Long> value = root.get(field)
                                           .as(Long.class);
        Long filter = Long.parseLong(columnfilter.getFilter());
//        if (IN_RANGE.equals(columnfilter.getType())) {
//            return cb.between(value, dateFrom, OffsetDateTime.parse(columnfilter.getDateTo(), dateParser));
//        }
        return predicateMap.computeIfAbsent(columnfilter.getType(), k -> {
                               throw new IllegalArgumentException("No such criteria [" + columnfilter.getType() + "]!? for Date Filter ");
                           })
                           .apply(cb, value, filter);

    }
}
