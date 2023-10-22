package modell.quarkus.dao;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@Transactional
public abstract class Resource<T extends BaseEntityInterface<K>, K> {
    @Inject
    Dao<T, K> modellDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public T get(@PathParam("id") K id) {
        return modellDao.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public T post(T source) {
        return modellDao.persist(source);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public T put(T source) {
        return modellDao.update(source);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") K id) {
        modellDao.remove(id);
    }
}