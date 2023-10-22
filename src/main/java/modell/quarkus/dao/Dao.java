package modell.quarkus.dao;


import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modell.quarkus.exceptions.ExpectedException;
import modell.quarkus.exceptions.UnexpectedException;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Dependent
public class Dao<T extends BaseEntityInterface<?>> {

    public static final String ID = "id";

    protected final Class<T> type;
    protected final String name;

    @PersistenceContext
    protected EntityManager em;

    @Inject
    DurationLogger dLog;

    /**
     * Constructs the Dao based on the provided class at the injection point:
     * Example - a Dao for MyClass TypeClass
     *
     * @param ip
     * @Inject Dao&#x3C;MyClass&#x3E; myClassDao;
     */
    @Inject
    public Dao(InjectionPoint ip) {
        this(((Class<T>) (((ParameterizedType) ip.getType()).getActualTypeArguments()[0])));
    }

    /**
     * Constructs the Dao based on the provided class in the extending Daos.
     *
     * @param type
     */
    protected Dao(Class<T> type) {
        this.type = type;
        this.name = "Dao<" + this.type.getSimpleName() + ">";
    }

    protected EntityManager em() {
        return em;
    }

    /**
     * Finds an entity in the database of the Generic Dao Type.
     *
     * @param id - the primary key to use, must be not null
     * @return - the entity for the primary key or throws ExpectedException if no entity is found
     */
    public T findById(Object id) {
        return findById(id, true);
    }

    /**
     * Finds an entity in the database of the Generic Dao Type.
     *
     * @param id      - the primary key to use, must be not null
     * @param notNull - if a persisted entity must exists
     * @return the entity for the primary key or throws ExpectedException if no entity is found and notNull is set to true.
     */
    public T findById(Object id, boolean notNull) {
        return resultAs(em.find(type, checkIfNotNull(id)), notNull);
    }

    /**
     * Given an entity (e.g. received from front end)
     * it locates the corresponding entity based on
     * the Primary Key in the database and returns it.
     *
     * @param source - the object that contains the id.
     * @return the persisted entity, if any or ExpectedException if no entity is found
     */
    public T findPersisted(BaseEntityInterface<?> source) {
        return this.findById(checkIfNotNull(source.getId()));
    }

    /**
     * Given an entity (e.g. received from front end)
     * it updates the database.
     * The code locates the corresponding persisted entity based on the
     * Primary Key in the database and calls the corresponding update Method.
     * To cover all possible cases for sub-entities, the merge method is used to save the data.
     * The Entity with the given Id must exist in the Database or a UnexpectedException is thrown.
     *
     * @param source - the object that contains the id.
     * @return the persisted entity, if any.
     */
    public T update(T source) {
        return em.merge(findPersisted(source).update(source));
    }

    /**
     * Returns all Entities persisted in the associated Table.
     *
     * @return
     */
    public List<T> findAll() {
        dLog.start(this.name + ".findAll()");
        CriteriaQuery<T> query = em.getCriteriaBuilder()
                                   .createQuery(this.type);
        Root<T> entity = query.from(type);
        final List<T> resultList = em.createQuery(query.select(entity))
                                     .getResultList();
        dLog.finish(this.name + ".findAll()");
        return resultList;
    }

    /**
     * Returns all persisted entities within the given list of ids.
     *
     * @param filter
     * @return
     */
    public List<T> findByIds(List<Long> filter) {
        return findByColumnValue(ID, filter);
    }

    /**
     * Returns all entities within the given list of ids from the input.
     * Input can be of entities of another type, but each must extend BaseEntityInterface.
     *
     * @param filter
     * @return
     */
    public List<T> findByOtherIds(List<? extends BaseEntityInterface> filter) {
        return findByColumnValue(ID, filter.stream()
                                           .map(BaseEntityInterface::getId)
                                           .collect(Collectors.toList()));
    }

    /**
     * Returns all persisted entities within the given list of ids from Entities in the input.
     *
     * @param filter - given entities with ID
     * @return
     */
    public List<T> findPersisted(List<T> filter) {
        return findByColumnValue(ID, filter.stream()
                                           .map(BaseEntityInterface::getId)
                                           .collect(Collectors.toList()));
    }

    /**
     * Queries the Database for entities that match a given list of values in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the List of filtered values
     * @return
     */
    public List<T> findByColumnValue(String filterColumn, List<? extends Object> filter) {
        return findByColumnValue(filterColumn, filter, true);
    }

    /**
     * Queries the Database for entities that match a given list of values in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the List of filtered values
     * @param notNull      - if list of filtered values can be null :  specifies if the filter value can be null, and in this case the null is used as filter.
     * @return
     */
    public List<T> findByColumnValue(String filterColumn, List<? extends Object> filter, boolean notNull) {
        CriteriaQuery<T> query = em.getCriteriaBuilder()
                                   .createQuery(this.type);
        Root<T> entity = query.from(type);
        if (applyFilter(filter, notNull)) {
            return em
                    .createQuery(query
                            .select(entity)
                            .where(entity
                                    .get(columnFrom(filterColumn))
                                    .in(filter)))
                    .getResultList();
        } else {
            return em
                    .createQuery(query
                            .select(entity)
                            .where(entity
                                    .get(columnFrom(filterColumn))
                                    .isNull()))
                    .getResultList();
        }
    }

    /**
     * Queries the Database for entities that match a specific value in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the value to filter for
     * @return
     */
    public List<T> listByColumnValue(String filterColumn, Object filter) {
        return listByColumnValue(filterColumn, filter, true);
    }

    /**
     * Queries the Database for entities that match a specific value in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the value to filter for
     * @param notNull      - specifies if the filter value can be null, and in this case the null is used as filter.
     * @return
     */
    public List<T> listByColumnValue(String filterColumn, Object filter, boolean notNull) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> entity = query.from(type);
        if (applyFilter(filter, notNull)) {
            return em
                    .createQuery(
                            query
                                    .select(entity)
                                    .where(criteriaBuilder
                                            .equal(entity
                                                    .get(columnFrom(filterColumn)), filter)))
                    .getResultList();
        } else {
            return em
                    .createQuery(
                            query
                                    .select(entity)
                                    .where(entity
                                            .get(columnFrom(filterColumn))
                                            .isNull()))
                    .getResultList();
        }
    }

    /**
     * Queries the Database for one entity that matches a specific value in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the value to filter for
     * @return
     */
    public T findByColumnValue(String filterColumn, Object filter) {
        return findByColumnValue(filterColumn, filter, true);
    }

    /**
     * Queries the Database for one entity that matches a specific value in a specified column.
     *
     * @param filterColumn - the column to filter for
     * @param filter       - the value to filter for
     * @param notNull      - specifies if the filter value can be null, and in this case the null is used as filter.
     * @return
     */
    public T findByColumnValue(String filterColumn, Object filter, boolean notNull) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            Root<T> entity = query.from(type);
            if (applyFilter(filter, notNull)) {
                return em
                        .createQuery(query
                                .select(entity)
                                .where(criteriaBuilder
                                        .equal(entity
                                                .get(columnFrom(filterColumn)), filter)))
                        .getSingleResult();
            } else {
                return em
                        .createQuery(query
                                .select(entity)
                                .where(entity
                                        .get(columnFrom(filterColumn))
                                        .isNull()))
                        .getSingleResult();
            }
        } catch (NoResultException exception) {
            return resultAs(exception, notNull);
        } catch (NonUniqueResultException exception) {
            throw new ExpectedException(this.name + ":Filtered Entity is not unique.");
        }
    }

    /**
     * Removes an entity
     *
     * @param t
     */
    public void remove(T t) {
        em.remove(checkIfNotNull(t));
    }

    /**
     * Removes an entity
     *
     * @param id
     */
    public void remove(Long id) {
        em.remove(this.findById(id));
    }


    /**
     * Removes all the entities with the id from the List.
     *
     * @param ids
     */
    public void removeByIds(List<Long> ids) {
        this.findByIds(ids)
            .forEach(this::remove);
    }

    /**
     * Merges the entity
     *
     * @param t
     * @return
     */
    public T merge(T t) {
        return em.merge(checkIfNotNull(t));
    }

    /**
     * Merges the entity
     *
     * @param t
     * @return
     */
    /**
     * Merges all the entities in the list, returning the results in a list.
     *
     * @param collectionOfT
     * @return
     */
    public List<T> merge(Collection<T> collectionOfT) {
        return collectionOfT.stream()
                            .map(this::merge)
                            .collect(Collectors.toList());
    }

    /**
     * Persists an entity
     *
     * @param source
     * @return
     */
    public T persist(T source) {
        em.persist(checkIfNotNull(source));
        return source;
    }

    /**
     * Persists or updates an entity
     *
     * @param source
     * @return
     */
    public T put(T source) {
        if (null == source.getId()) {
            return this.persist(source);
        } else {
            return this.update(source);
        }
    }

    /**
     * Persists or updates a list of entities
     *
     * @param collectionOfT
     * @return
     */
    public List<T> put(Collection<T> collectionOfT) {
        return collectionOfT.stream()
                            .map(this::put)
                            .collect(Collectors.toList());
    }


    /**
     * Returns all the objects from the table in a Map Cache by one unique Key
     *
     * @param getUniqueKey
     * @param <E>
     * @return
     */
    public <E> Map<E, T> mapAllByUniqueKey(Function<T, E> getUniqueKey) {
        return Cache.mapByUniqueKey(this.findAll(), getUniqueKey);
    }

    /**
     * Returns all the objects from the table in a Map Cache by one unique Key
     * The objects can be filtered by means of a provided predicate
     * The filter will be applied on the entity (T)
     * //to do : solution permits an optimization by moving the filtering in the Cache class itself
     * (moving the parameter down the chain)
     *
     * @param getUniqueKey
     * @param <E>
     * @return
     */
    public <E> Map<E, T> mapAllByFilteredUniqueKey(Function<T, E> getUniqueKey, Predicate<T> filter) {
        return Cache.mapByUniqueKey(
                this.findAll()
                    .stream()
                    .filter(filter)
                    .collect(Collectors.toList()),
                getUniqueKey);
    }

    /**
     * Returns all the objects from the table in a Map Cache by one unique Key
     * The objects can be filtered by means of a provided filter function
     * The filter will be applied on the key value
     * //to do : solution permits an optimization by moving the filtering in the Cache class itself
     * (moving the parameter down the chain)
     *
     * @param getUniqueKey
     * @param <E>
     * @return
     */
    public <E> Map<E, T> mapAllByFilteredUniqueKey(Function<T, E> getUniqueKey, Function<E, Boolean> filter) {
        return Cache.mapByUniqueKey(
                this.findAll()
                    .stream()
                    .filter(entity -> filter.apply(getUniqueKey.apply(entity)))
                    .collect(Collectors.toList()),
                getUniqueKey);
    }

    /**
     * Returns all the objects from the table in a Map Cache by one non-unique Key
     *
     * @param getKey
     * @param getValue
     * @param <E>
     * @param <F>
     * @return
     */
    public <E, F> Map<E, Set<F>> mapAllByKey(Function<T, E> getKey, Function<T, F> getValue) {
        return Cache.mapByKey(this.findAll(), getKey, getValue);
    }

    /**
     * Returns all the objects from the table in a Map Cache by two unique Keys
     *
     * @param getUniqueKey1
     * @param getUniqueKey2
     * @param <E>
     * @param <F>
     * @return
     */
    public <E, F> Map<E, Map<F, T>> mapAllByUniqueKeys(Function<T, E> getUniqueKey1, Function<T, F> getUniqueKey2) {
        return Cache.mapByUniqueKeys(this.findAll(), getUniqueKey1, getUniqueKey2);
    }

    protected <R> R fetchOne(TypedQuery<R> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return resultAs(nre, false);
        }
    }

    protected <R> R checkIfNotNull(R source) {
        if (null == source) {
            throw new UnexpectedException(this.name + ":Expected not null Entity or id.");
        }
        return source;
    }

    protected <R> R resultAs(R source, boolean notNull) {
        if (notNull && null == source) {
            throw new ExpectedException(this.name + ":Entity was not found");
        }
        return source;
    }

    protected <T> T resultAs(NoResultException exception, boolean notNull) {
        if (notNull) {
            throw new ExpectedException(this.name + ":Entity was not found", exception);
        }
        return null;
    }

    protected String columnFrom(String column) {
        if (column == null || column.isBlank()) {
            throw new UnexpectedException(this.name + ":Expected a table column name.");
        }
        return column;
    }

    protected <R> boolean applyFilter(R filter, boolean notNull) {
        if (null == filter) {
            if (notNull) {
                throw new UnexpectedException(this.name + ":Expected not null Filter.");
            }
            return false;
        }
        return !(filter instanceof List<?>) || !((List<?>) filter).isEmpty();
    }

}
