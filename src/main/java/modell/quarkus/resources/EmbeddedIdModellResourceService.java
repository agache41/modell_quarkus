package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lombok.Getter;
import modell.quarkus.entities.EmbeddedIdModell;
import modell.quarkus.entities.EmbeddedKeys;


@Getter
@ApplicationScoped
@Path("/embeddedIdModell")
public class EmbeddedIdModellResourceService extends AbstractResourceServiceImpl<EmbeddedIdModell, EmbeddedIdModell, EmbeddedKeys> {

}