package modell.quarkus.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import modell.quarkus.dao.Dao;
import modell.quarkus.entities.Modell;

@ApplicationScoped
public class ModellService {
    @Inject
    Dao<Modell> modellDao;

    @Transactional
    public Modell getById(Long id) {
        return modellDao.findById(id);
    }

    @Transactional
    public void createGift(String giftDescription) {
        Modell modell = new Modell();
        modell.setStringValue(giftDescription);
        modellDao.persist(modell);
    }
}