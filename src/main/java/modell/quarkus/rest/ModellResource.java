package modell.quarkus.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import lombok.Data;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.rest.Resource;

@Data
@Path("/modell")
public class ModellResource extends Resource<Modell, Long> {

    @Inject
    protected ModellDataAccess dataAccess;

}