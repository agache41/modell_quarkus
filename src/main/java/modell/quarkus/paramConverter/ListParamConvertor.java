package modell.quarkus.paramConverter;

import jakarta.ws.rs.ext.ParamConverter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ListParamConvertor<T> implements ParamConverter<List<T>> {

    private final Function<String, T> parse;
    private final Function<T, String> format;

    public ListParamConvertor(Function<String, T> parse, Function<T, String> format) {
        this.parse = parse;
        this.format = format;
    }

    public ListParamConvertor(Function<String, T> parse) {
        this(parse, Object::toString);
    }

    public ListParamConvertor() {
        this(s -> (T) s, t -> (String) t);
    }

    @Override
    public List<T> fromString(String value) {
        if (value == null || value.isEmpty()) return Collections.emptyList();
        return Stream.of(value.split("[[,]]")).map(this.parse).collect(Collectors.toList());
    }

    @Override
    public String toString(List<T> value) {
        if (value == null || value.isEmpty()) return "[]";
        return value
                .stream()
                .map(this.format)
                .collect(Collectors.joining(","));
    }
}
