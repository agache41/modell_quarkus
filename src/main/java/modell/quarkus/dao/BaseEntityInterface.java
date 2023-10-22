package modell.quarkus.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import modell.quarkus.exceptions.UnexpectedException;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface for Entity's that need Sequence generator.
 */
public interface BaseEntityInterface<KEY_T> {

    /**
     * Default allocation size.
     */
    int ALLOCATION_SIZE = 1;

    /**
     * Allocation size for entities used in batch actions.
     */
    int BATCH_ALLOCATION_SIZE = 1000;

    /**
     * Returns the BaseEntity for this Entity.
     * Method is null safe.
     * The Base Entity is a Transport Object with just the id of the Source Entity.
     *
     * @param source
     * @param <T>
     * @param <KEY_T>
     * @return
     */
    static <T extends BaseEntityInterface<KEY_T>, KEY_T> BaseEntity<KEY_T> getBaseEntity(T source) {
        BaseEntity<KEY_T> result = null;
        if (source != null) {
            result = source.getBaseEntity();
        }
        return result;
    }

    /**
     * Returns the corresponding Entity for this BaseEntity, given the expected Entity class.
     * Method is null safe.
     * Its result is not the complete entity,
     * but just one pre-filled with the id, and the result is meant to be used just in merge operations.
     *
     * @param source
     * @param clazz
     * @param <T>
     * @param <KEY_T>
     * @return
     */
    static <T extends BaseEntityInterface<KEY_T>, KEY_T> T getEntity(BaseEntity<KEY_T> source, Class<T> clazz) {
        T result = null;
        if (source != null) {
            result = source.getEntity(clazz);
        }
        return result;
    }

    /**
     * This method is for force implement ID attribute in Entity's.
     *
     * @return return Id.
     */
    KEY_T getId();

    /**
     * This method is for force implement ID attribute in Entity's.
     *
     * @param id to set
     */
    void setId(KEY_T id);

    /**
     * Updates the current object from the given source.
     * The method works in tandem with the @Write annotation
     * Only the marked fields will be updated, null values rules are respected.
     * Also NotNull annotation is checked.
     *
     * @param source to update from
     * @return current update object
     */
    default <T extends BaseEntityInterface<?>> T update(T source) {
        return source;
        // todo:
        //return ClassDescriptor.getDescriptor(source)
        //                      .update((T) this, source);
    }

    /**
     * Returns the BaseEntity for this Entity.
     * The Base Entity is a Transport Object with just the id of the Source Entity
     *
     * @return
     */
    //@JsonbTransient
    @JsonIgnore
    default BaseEntity<KEY_T> getBaseEntity() {
        return new BaseEntity(this.getId());
    }

    /**
     * Returns the corresponding Entity for this BaseEntity, given the expected Entity class.
     * Its result is not the complete entity,
     * but just one pre-filled with the id, and the result is meant to be used just in merge operations.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    //@JsonbTransient
    @JsonIgnore
    default <T extends BaseEntityInterface<KEY_T>> T getEntity(Class<T> clazz) {
        try {
            final T result = clazz.getDeclaredConstructor()
                                  .newInstance();
            result.setId(this.getId());
            return result;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new UnexpectedException(" When trying to read back the id in IdEntity", e);
        }
    }
}
