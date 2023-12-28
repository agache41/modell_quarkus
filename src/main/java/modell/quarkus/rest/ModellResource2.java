package modell.quarkus.rest;

import jakarta.ws.rs.Path;
import lombok.Data;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.rest.Resource;

@Data
@Path("/modell2")
public class ModellResource2 extends Resource<Modell, Long> {

}