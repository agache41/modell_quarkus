package modell.quarkus.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import modell.quarkus.entities.Modell;

@ApplicationScoped
public class ModellService {
    @Inject
    EntityManager em;


    @Transactional
    public Modell getById(Long id) {
        return em.find(Modell.class, id);
    }

    @Transactional
    public void createGift(String giftDescription) {
        Modell modell = new Modell();
        modell.setName(giftDescription);
        em.persist(modell);
    }


}