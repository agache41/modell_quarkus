package modell.quarkus.dao;

import jakarta.inject.Inject;
import modell.quarkus.entities.Modell;

public class DaoService {
    @Inject
    Dao<Modell> modellDao;


}
