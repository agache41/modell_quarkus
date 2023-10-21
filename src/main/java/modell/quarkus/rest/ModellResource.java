package modell.quarkus.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modell.quarkus.entities.Modell;
import modell.quarkus.services.GreetingService;
import modell.quarkus.services.ModellService;

@Path("/modell")
public class ModellResource {

    @Inject
    GreetingService service;

    @Inject
    ModellService modellService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Modell get(Long id) {
        return modellService.getById(id);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public String greeting(String name) {
        return service.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
}