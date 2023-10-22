package modell.quarkus.dao;

import io.smallrye.common.constraint.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public final class FieldDescriptor<T> {
    private static final String GETTER_PREFIX = "get";
    private static final String GETTER_PRIM_BOOL_PREFIX = "is";
    private static final String SETTER_PREFIX = "set";
    private static final Object[] NULL = new Object[]{null};
    private final String name;
    private final Method readMethod;
    private final Method writeMethod;
    private final boolean notNull;

    FieldDescriptor(Class<T> workingClass, Field field) {
        this.name = field.getName();
        try {
            this.writeMethod = FieldDescriptor.getSetter(workingClass, field);
            this.readMethod = FieldDescriptor.getGetter(workingClass, field);
            this.notNull = field.getAnnotation(Write.class)
                                .notNull() || field.isAnnotationPresent(NotNull.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Method getGetter(Class<?> definingClass, Field field) {
        try {
            // the getter method to use
            return definingClass.getDeclaredMethod(
                    (boolean.class.equals(field.getType()) ?
                            GETTER_PRIM_BOOL_PREFIX :
                            GETTER_PREFIX) +
                            StringUtils.capitalize(field.getName()),
                    null);
        } catch (SecurityException | NoSuchMethodException e) { // getter is faulty
            throw new IllegalArgumentException(e.getMessage() + " when getting getter for " + field.getName() + " in class " + definingClass.getCanonicalName(), e);
        }
    }

    public static Method getSetter(Class<?> definingClass, Field field) {
        try {
            // the setter method to use
            return definingClass.getDeclaredMethod(
                    SETTER_PREFIX +
                            StringUtils.capitalize(field.getName()),
                    field.getType());
        } catch (SecurityException | NoSuchMethodException e) { // setter is faulty
            throw new IllegalArgumentException(e.getMessage() + " when getting setter for " + field.getName() + " in class " + definingClass.getCanonicalName(), e);
        }
    }

    public static boolean isClassCollection(Class c) {
        return Collection.class.isAssignableFrom(c);
    }

    public static boolean isCollection(Object ob) {
        return ob != null && isClassCollection(ob.getClass());
    }

    public static boolean isMapCollection(Class c) {
        return Map.class.isAssignableFrom(c);
    }

    public static boolean isMap(Object ob) {
        return ob != null && isMapCollection(ob.getClass());
    }

    /**
     * Given the source and destination objects, does the update for the associated field.
     *
     * @param destination
     * @param source
     * @return
     */
    public Object update(T destination, T source) {
        try {
            final Object sourceValue = readMethod.invoke(source);
            // not null default case
            if (null == sourceValue && this.notNull) {
                return null;
            }
            // null case for entities
            if (sourceValue instanceof BaseEntityInterface<?>) {
                BaseEntityInterface<?> entity = (BaseEntityInterface) sourceValue;
                //if the provide entity is nullified
                if (entity.getId() == null) {
                    if (this.notNull) {
                        // not null default case
                        return null;
                    } // set field to null to break relation.
                    return writeMethod.invoke(destination, NULL);
                }
            }
            // fix for using cascade all on hibernate
            if (isCollection(sourceValue)) {
                final Collection destinationCollection = (Collection) readMethod.invoke(destination);
                if (destinationCollection != null) {
                    destinationCollection.clear();
                    destinationCollection.addAll((Collection) sourceValue);
                    return destinationCollection;
                }
            }
            if (isMap(sourceValue)) {
                final Map destinationMap = (Map) readMethod.invoke(destination);
                if (destinationMap != null) {
                    destinationMap.clear();
                    destinationMap.putAll((Map) sourceValue);
                    return destinationMap;
                }
            }
            return writeMethod.invoke(destination, sourceValue);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getName() {
        return name;
    }
}
