package modell.quarkus.contract.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.contract.classes.CombinedSimpleModel;
import org.apache.commons.lang3.function.TriFunction;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateCriteriaFilter implements ICriteriaFilter {

    protected final DateTimeFormatter dateParser = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("+02:00"));

    protected static final String GREATER_THAN = "greaterThan";
    protected static final String LESS_THAN = "lessThan";
    protected static final String IN_RANGE = "inRange";

    protected static final String EQUALS = "equals";
    protected static final String NOT_EQUAL = "notEqual";


    protected static final Map<String, TriFunction<CriteriaBuilder, Expression<OffsetDateTime>, OffsetDateTime, Predicate>> predicateMap = new HashMap<>();

    static {
        predicateMap.put(GREATER_THAN, CriteriaBuilder::greaterThanOrEqualTo);
        predicateMap.put(LESS_THAN, CriteriaBuilder::lessThan);
        predicateMap.put(EQUALS, CriteriaBuilder::equal);
        predicateMap.put(NOT_EQUAL, CriteriaBuilder::notEqual);
    }

    @Override
    public <ENTITY> Predicate predicate(CriteriaBuilder cb, Root<ENTITY> root, String field, CombinedSimpleModel columnfilter, Object[] parameters) {
        final Expression<OffsetDateTime> value = root.get(field)
                                                     .as(OffsetDateTime.class);
        OffsetDateTime dateFrom = OffsetDateTime.parse(columnfilter.getDateFrom(), dateParser);
        if (IN_RANGE.equals(columnfilter.getType())) {
            return cb.between(value, dateFrom, OffsetDateTime.parse(columnfilter.getDateTo(), dateParser));
        }
        return predicateMap.computeIfAbsent(columnfilter.getType(), k -> {
                               throw new IllegalArgumentException("No such criteria [" + columnfilter.getType() + "]!? for Date Filter ");
                           })
                           .apply(cb, value, dateFrom);

    }
}
