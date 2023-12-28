package modell.quarkus.rest;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.dao.DataAccess;

@Dependent
public class ModellDataAccess extends DataAccess<Modell, Long> {

    @Inject
    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

    @Override
    public Modell findById(Long id) {
        return super.findById(id);
    }
}
