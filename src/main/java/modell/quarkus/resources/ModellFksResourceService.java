package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import modell.quarkus.entities.ModellFks;


@ApplicationScoped
@Path("/modellFks")
public class ModellFksResourceService extends AbstractResourceServiceImpl<ModellFks, ModellFks, Long> {


}