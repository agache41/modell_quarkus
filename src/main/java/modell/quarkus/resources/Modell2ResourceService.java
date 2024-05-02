package modell.quarkus.resources;


import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.Getter;
import modell.quarkus.entities.Modell2;


@Getter
@Path("/modell2")
@Transactional
public class Modell2ResourceService extends AbstractResourceServiceImpl<Modell2, String> {
}