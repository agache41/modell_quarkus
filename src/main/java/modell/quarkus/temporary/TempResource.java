package modell.quarkus.temporary;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.structured.api.quarkus.dao.DataAccess;
import org.structured.api.quarkus.dao.PrimaryKey;

public abstract class TempResource<T extends PrimaryKey<K>, K> {

    @Inject
    @Named("base")
    protected DataAccess<T, K> dataAccess;

    protected DataAccess<T, K> getDataAccess() {
        return dataAccess;
    }

    // new ideas here
}
