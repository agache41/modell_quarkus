package modell.quarkus.temporary;

import jakarta.ws.rs.ext.ParamConverter;

import java.util.Arrays;
import java.util.List;

public class ListConverter implements ParamConverter<List<Long>> {

    @Override
    public List<Long> fromString(String value) {
        if (value == null)
            return null;
        return Arrays.asList(1L, 2L, 3L, 4L, 5L);
    }

    @Override
    public String toString(List<Long> value) {
        if (value == null)
            return null;
        return value.toString();
    }
}