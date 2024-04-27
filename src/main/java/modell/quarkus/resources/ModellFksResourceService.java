package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.entities.ModellFks;


@Path("/modellFks")
@Transactional
public class ModellFksResourceService extends AbstractResourceServiceImpl<ModellFks, Long> {


}