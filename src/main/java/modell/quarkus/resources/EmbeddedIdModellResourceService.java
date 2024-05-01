package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.Getter;
import modell.quarkus.entities.EmbeddedIdModell;
import modell.quarkus.entities.EmbeddedKeys;


@Getter
@Path("/embeddedIdModell")
@Transactional
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedKeys> {

}