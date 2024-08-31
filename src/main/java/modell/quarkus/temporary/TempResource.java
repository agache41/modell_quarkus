package modell.quarkus.temporary;

import io.github.agache41.generic.rest.jpa.dataAccess.DataAccess;
import io.github.agache41.generic.rest.jpa.dataAccess.PrimaryKey;
import io.github.agache41.generic.rest.jpa.update.SelfTransferObject;
import jakarta.inject.Inject;
import jakarta.inject.Named;

public abstract class TempResource<T extends PrimaryKey<K> & SelfTransferObject<T>, K> {

    @Inject
    @Named("base")
    protected DataAccess<T, K> dataAccess;

    protected DataAccess<T, K> getDataAccess() {
        return dataAccess;
    }

    // new ideas here
}
