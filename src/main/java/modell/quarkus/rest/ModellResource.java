package modell.quarkus.rest;

import jakarta.ws.rs.Path;
import modell.quarkus.dao.Resource;
import modell.quarkus.entities.Modell;

@Path("/modell")
public class ModellResource extends Resource<Modell, Long> {
}