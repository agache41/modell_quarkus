package modell.quarkus.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modell.quarkus.dao.Dao;
import modell.quarkus.entities.Modell;

@Path("/modell")
@Transactional
public class ModellResource {
    @Inject
    Dao<Modell> modellDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Modell get(@PathParam("id") Long id) {
        return modellDao.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Modell post(Modell source) {
        return modellDao.persist(source);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Modell put(Modell source) {
        return modellDao.update(source);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        modellDao.remove(id);
    }
}