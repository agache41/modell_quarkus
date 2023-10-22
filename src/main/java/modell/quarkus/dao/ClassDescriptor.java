package modell.quarkus.dao;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ClassDescriptor {

    private static final Map<Class<?>, ClassDescriptor> concurrentClassDescriptorsCache = new ConcurrentHashMap<>();
    private final Map<String, FieldDescriptor> fieldDescriptors;

    //todo: add use case for Write annotation on the class , for all fields
    //todo: add Write.Exclude annotation
    private ClassDescriptor(Class<?> sourceClass) {
        this.fieldDescriptors = FieldUtils
                .getFieldsListWithAnnotation(sourceClass, Write.class)
                .stream()
                .map(field -> new FieldDescriptor(sourceClass, field))
                .collect(Collectors.toMap(FieldDescriptor::getName, Function.identity()));
    }

    /**
     * Given an object of a class, it returns the associated classdescriptor.
     * There will be just one class descriptor per class (Singleton)
     *
     * @param source
     * @return
     */
    public static ClassDescriptor getDescriptor(BaseEntityInterface<?> source) {
        final Class<?> sourceClass = source.getClass();
        return concurrentClassDescriptorsCache.computeIfAbsent(sourceClass, ClassDescriptor::new);
    }

    /**
     * Given a source and destination, it will update all corresponding fields according to the @Write annotation.
     *
     * @param destination
     * @param source
     * @return
     */
    public <T extends BaseEntityInterface<?>> T update(T destination, T source) {
        this.fieldDescriptors.values()
                             .forEach(fieldDescriptor -> fieldDescriptor.update(destination, source));
        return destination;
    }
}