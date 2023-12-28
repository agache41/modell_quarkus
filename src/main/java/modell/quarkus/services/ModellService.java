package modell.quarkus.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.dao.DataAccess;

@ApplicationScoped
public class ModellService {
    @Inject
    @Named("base")
    DataAccess<Modell, Long> modellDataAccess;

    @Transactional
    public Modell getById(Long id) {
        return modellDataAccess.findById(id);
    }

    @Transactional
    public void createGift(String giftDescription) {
        Modell modell = new Modell();
        modell.setStringValue(giftDescription);
        modellDataAccess.persist(modell);
    }
}