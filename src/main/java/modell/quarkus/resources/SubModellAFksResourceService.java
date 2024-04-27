package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.entities.SubModellAFks;


@Path("/subModellAFks")
@Transactional
public class SubModellAFksResourceService extends AbstractResourceServiceImpl<SubModellAFks, Long> {


}