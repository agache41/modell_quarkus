package modell.quarkus.utility.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modell.quarkus.utility.dao.DataAccess;
import modell.quarkus.utility.dao.PrimaryKey;


@Transactional
public abstract class Resource<T extends PrimaryKey<K>, K> {
    @Inject
    DataAccess<T, K> modellDataAccess;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public T get(@PathParam("id") K id) {
        return modellDataAccess.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public T post(T source) {
        return modellDataAccess.merge(source);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public T put(T source) {
        return modellDataAccess.updateById(source);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") K id) {
        modellDataAccess.removeById(id);
    }
}