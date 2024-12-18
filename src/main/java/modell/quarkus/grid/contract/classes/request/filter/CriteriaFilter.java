package modell.quarkus.grid.contract.classes.request.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.grid.contract.classes.request.CombinedSimpleModel;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Map;

public abstract class CriteriaFilter<FIELD extends Comparable<FIELD>> implements ICriteriaFilter<FIELD> {
    protected static final String GREATER_THEN = "greaterThan";
    protected static final String GREATER_THEN_OR_EQUAL = "greaterThanOrEqual";
    protected static final String LESS_THEN = "lessThan";
    protected static final String LESS_THEN_OR_EQUAL = "lessThanOrEqual";
    protected static final String IN_RANGE = "inRange";
    protected static final String EQUALS = "equals";
    protected static final String NOT_EQUAL = "notEqual";

    protected final Map<String, TriFunction<CriteriaBuilder, Expression<FIELD>, FIELD, Predicate>> predicateMap;

    public CriteriaFilter() {
        this.predicateMap = Map.ofEntries(//
                Map.entry(GREATER_THEN, CriteriaBuilder::greaterThan), //
                Map.entry(GREATER_THEN_OR_EQUAL, CriteriaBuilder::greaterThanOrEqualTo), //
                Map.entry(LESS_THEN, CriteriaBuilder::lessThan),//
                Map.entry(LESS_THEN_OR_EQUAL, CriteriaBuilder::lessThanOrEqualTo),//
                Map.entry(EQUALS, CriteriaBuilder::equal),//
                Map.entry(NOT_EQUAL, CriteriaBuilder::notEqual));//
    }

    @Override
    public <ENTITY> Predicate predicate(CriteriaBuilder cb, Root<ENTITY> root, Expression<FIELD> fieldValue, Class<FIELD> fieldType, CombinedSimpleModel columnfilter, Object[] parameters) {
        FIELD filter = TypeParser.parse(fieldType, filter(columnfilter));
        if (IN_RANGE.equals(columnfilter.getType())) {
            return cb.between(fieldValue, filter, TypeParser.parse(fieldType, filterTo(columnfilter)));
        }
        return predicateMap.computeIfAbsent(columnfilter.getType(), k -> {
                               throw new IllegalArgumentException("No such criteria [" + columnfilter.getType() + "]!? for Date Filter ");
                           })
                           .apply(cb, fieldValue, filter);
    }

    protected abstract String filter(CombinedSimpleModel columnFilter);
    protected abstract String filterTo(CombinedSimpleModel columnFilter);
}
