package modell.quarkus.rest;

import jakarta.ws.rs.Path;
import modell.quarkus.entities.Modell;
import modell.quarkus.utility.rest.Resource;

@Path("/modell")
public class ModellResource extends Resource<Modell, Long> {
}