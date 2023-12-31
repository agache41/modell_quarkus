package modell.quarkus.paramConverter.provider;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import modell.quarkus.paramConverter.IntegerListParamConverter;
import modell.quarkus.paramConverter.LocalDateParamConverter;
import modell.quarkus.paramConverter.LongListParamConverter;
import modell.quarkus.paramConverter.StringListParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider
public class ListParamConverterProvider implements ParamConverterProvider {

    private static final Map<Type, ParamConverter<?>> paramConverterMap = new HashMap<>();

    static {
        paramConverterMap.put(String.class, new StringListParamConverter());
        paramConverterMap.put(Integer.class, new IntegerListParamConverter());
        paramConverterMap.put(Long.class, new LongListParamConverter());
        paramConverterMap.put(LocalDate.class, new LocalDateParamConverter());
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
                                              Annotation[] annotations) {
        if (rawType.equals(List.class)) {
            ParamConverter<?> paramConverter = null;
            if (genericType instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) genericType;
                Type[] actualTypeArguments = ptype.getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    Type parameterType = actualTypeArguments[0];
                    paramConverter = paramConverterMap.get(parameterType);
                }
            }
            if (paramConverter == null) {
                System.out.println("No Parameter Converter found for Class " + rawType.getSimpleName() + " with generic " + genericType.getTypeName());
            }
            return (ParamConverter<T>) paramConverter;
        }
        return null;
    }
}