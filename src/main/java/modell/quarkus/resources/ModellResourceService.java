package modell.quarkus.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.resourceService.AbstractResourceServiceImpl;


@Path("/modell")
@Transactional
public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Long> {


}