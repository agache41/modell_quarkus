package modell.quarkus.temporary;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

//@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
                                              Annotation[] annotations) {
        if (rawType.equals(List.class))
            return (ParamConverter<T>) new ListConverter();
        return null;
    }

}