package modell.quarkus.contract.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.contract.classes.CombinedSimpleModel;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TextCriteriaFilter implements ICriteriaFilter {
    protected static final String CONTAINS = "contains";
    protected static final String NOT_CONTAINS = "notContains";
    protected static final String STARTS_WITH = "startsWith";
    protected static final String ENDS_WITH = "endsWith";
    protected static final String EQUALS = "equals";
    protected static final String NOT_EQUAL = "notEqual";
    protected static final String BLANK = "blank";
    protected static final String NOT_BLANK = "notBlank";

    protected static final Map<String, TriFunction<CriteriaBuilder, Expression<String>, String, Predicate>> predicateMap = new HashMap<>();

    static {
        predicateMap.put(CONTAINS, (cb, value, filter) -> cb.like(value, "%" + filter.replaceAll("\\s+", "%") + "%"));
        predicateMap.put(NOT_CONTAINS, (cb, value, filter) -> cb.notLike(value, "%" + filter.replaceAll("\\s+", "%") + "%"));
        predicateMap.put(STARTS_WITH, (cb, value, filter) -> cb.like(value, filter.replaceAll("\\s+", "%") + "%"));
        predicateMap.put(ENDS_WITH, (cb, value, filter) -> cb.like(value, "%" + filter.replaceAll("\\s+", "%")));
        predicateMap.put(EQUALS, CriteriaBuilder::equal);
        predicateMap.put(NOT_EQUAL, CriteriaBuilder::notEqual);
        predicateMap.put(BLANK, (cb, value, filter) -> cb.or(cb.isNull(value), cb.equal(value, "")));
        predicateMap.put(NOT_BLANK, (cb, value, filter) -> cb.and(cb.isNotNull(value), cb.notEqual(value, "")));
    }

    @Override
    public <ENTITY> Predicate predicate(CriteriaBuilder cb, Root<ENTITY> root, String field, CombinedSimpleModel columnfilter, Object[] parameters) {
        final Expression<String> value = cb.lower(root.get(field)
                                                      .as(String.class));
        final String filter = toLowerCase(columnfilter.getFilter());
        return predicateMap.computeIfAbsent(columnfilter.getType(), k -> {
                               throw new IllegalArgumentException("No such criteria [" + columnfilter.getType() + "]!? for Text Filter ");
                           })
                           .apply(cb, value, filter);

    }

    private String toLowerCase(String input) {
        if (input == null) return null;
        return input.toLowerCase(Locale.ROOT);
    }
}
