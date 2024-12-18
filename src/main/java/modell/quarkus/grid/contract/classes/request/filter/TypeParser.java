package modell.quarkus.grid.contract.classes.request.filter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class TypeParser {
    protected static final DateTimeFormatter zonedParser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                            .withZone(ZoneId.systemDefault());
    protected static final SimpleDateFormat parser = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a");

    protected static final Map<Class<?>, Function<String, ?>> parserMap = Map.ofEntries(//
            Map.entry(String.class, Function.identity()),//
            Map.entry(Byte.class, Byte::parseByte),//
            Map.entry(Integer.class, Integer::parseInt),//
            Map.entry(Long.class, Long::parseLong),//
            Map.entry(Double.class, Double::parseDouble),//
            Map.entry(Float.class, Float::parseFloat),//
            Map.entry(BigInteger.class, BigInteger::new),//
            Map.entry(BigDecimal.class, BigDecimal::new),//
            Map.entry(OffsetDateTime.class, string -> OffsetDateTime.parse(string, zonedParser)),//
            Map.entry(LocalDate.class, string -> LocalDate.parse(string, zonedParser)),//
            Map.entry(LocalDateTime.class, string -> LocalDateTime.parse(string, zonedParser)),//
            Map.entry(LocalTime.class, string -> LocalTime.parse(string, zonedParser)),//
            Map.entry(Date.class, string -> {//
                try {
                    return parser.parse(string);
                } catch (ParseException pex) {
                    throw new RuntimeException(pex);
                }
            }));

    @SuppressWarnings("unchecked")
    public static final <FIELD> FIELD parse(Class<FIELD> type, String input) {
        if (input == null) return null;
        return (FIELD) parserMap.computeIfAbsent(type, k -> {
                                    throw new IllegalArgumentException("No parser is available for such type [" + type.getSimpleName() + "]!? ");
                                })
                                .apply(input);
    }
}
