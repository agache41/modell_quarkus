package modell.quarkus.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import modell.quarkus.entities.Modell;
import modell.quarkus.utility.dao.DataAccess;

@ApplicationScoped
public class ModellService {
    @Inject
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