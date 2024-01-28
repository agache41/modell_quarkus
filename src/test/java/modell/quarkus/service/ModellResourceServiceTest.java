package modell.quarkus.service;

import io.github.agache41.generic.rest.jpa.filler.Producer;
import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import modell.quarkus.entities.Modell;

import java.util.List;

@QuarkusTest
@Transactional
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

    static final String path = "/modell";
    private static final String stringField = "name";
    private static final Producer<Modell> producer = Producer.ofClass(Modell.class);
    private static final List<Modell> insertData = producer.getList(2);
    private static final List<Modell> updateData = producer.applyList(insertData);

    static {
        producer.setCollectionSize(2);
    }

    public ModellResourceServiceTest() {
        super(Modell.class, //
              path, //
              insertData, //
              updateData,
              stringField); //
    }
}
