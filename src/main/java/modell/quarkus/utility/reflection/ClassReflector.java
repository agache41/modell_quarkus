package modell.quarkus.utility.reflection;

import modell.quarkus.utility.exceptions.UnexpectedException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ClassReflector<T> {

    private static final Map<Class<?>, ClassReflector> concurrentClassDescriptorsCache = new ConcurrentHashMap<>();

    private final Class<T> type;
    private final Map<String, FieldReflector> reflectors;
    private final Map<String, FieldReflector> updateReflectors;

    private ClassReflector(Class<T> sourceClass) {

        this.type = sourceClass;

        this.reflectors = FieldUtils.getAllFieldsList(sourceClass)
                                    .stream()
                                    .map(field -> new FieldReflector(sourceClass, field))
                                    .filter(FieldReflector::isValid)
                                    .collect(Collectors.toMap(FieldReflector::getName, Function.identity()));

        this.updateReflectors = this.reflectors.values()
                                               .stream()
                                               .filter(FieldReflector::isUpdateable)
                                               .collect(Collectors.toMap(FieldReflector::getName, Function.identity()));
    }

    /**
     * Given an object of a class, it returns the associated classdescriptor.
     * There will be just one class descriptor per class (Singleton)
     *
     * @param source
     * @return
     */
    public static <R> ClassReflector<R> ofClass(Class<R> clazz) {
        return concurrentClassDescriptorsCache.computeIfAbsent(clazz, ClassReflector::new);
    }

    public static <R> ClassReflector<R> ofObject(R object) {
        return concurrentClassDescriptorsCache.computeIfAbsent(object.getClass(), ClassReflector::new);
    }


    /**
     * Given a source and destination, it will update all corresponding fields according to the @Write annotation.
     *
     * @param destination
     * @param source
     * @return
     */
    public T update(T destination, T source) {
        this.updateReflectors.values()
                             .forEach(fieldReflector -> fieldReflector.update(destination, source));
        return destination;
    }

    public Object get(T source, String fieldName) {
        FieldReflector fieldReflector = this.reflectors.get(fieldName);
        if (fieldReflector == null)
            throw new UnexpectedException(" No such field " + fieldName + " in " + source.getClass()
                                                                                         .getSimpleName());
        return fieldReflector.get(source);
    }

    public void set(T source, String fieldName, Object value) {
        FieldReflector fieldReflector = this.reflectors.get(fieldName);
        if (fieldReflector == null)
            throw new UnexpectedException(" No such field " + fieldName + " in " + source.getClass()
                                                                                         .getSimpleName());
        fieldReflector.set(source, value);
    }


}