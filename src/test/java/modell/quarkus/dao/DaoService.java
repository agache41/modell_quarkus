package modell.quarkus.dao;

import jakarta.inject.Inject;
import modell.quarkus.entities.Modell;
import modell.quarkus.utility.dao.DataAccess;

public class DaoService {
    @Inject
    DataAccess<Modell, Long> modellDataAccess;


}
