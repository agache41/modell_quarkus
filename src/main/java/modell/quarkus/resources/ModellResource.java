package modell.quarkus.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.Data;
import modell.quarkus.dataaccess.ModellDataAccess;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.rest.AbstractResourceService;

@Data
@Path("/modell")
@Transactional
public class ModellResource extends AbstractResourceService<Modell, Long> {

    @Inject
    protected ModellDataAccess dataAccess;

}