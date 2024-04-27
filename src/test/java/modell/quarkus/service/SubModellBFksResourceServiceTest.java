package modell.quarkus.service;

import io.github.agache41.generic.rest.jpa.filler.Producer;
import io.github.agache41.generic.rest.jpa.modell.entities.SubModellBFks;
import io.github.agache41.generic.rest.jpa.resourceService.AbstractResourceServiceImplTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


@QuarkusTest
@Transactional
public class SubModellBFksResourceServiceTest extends AbstractResourceServiceImplTest<SubModellBFks, Long> {

    static final String path = "/subModellAFks";
    private static final String stringField = "subName";
    private static final int collectionSize = 16;
    private static final Producer<SubModellBFks> producer;
    private static final List<SubModellBFks> insertData;
    private static final List<SubModellBFks> updateData;

    static {
        producer = Producer.ofClass(SubModellBFks.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public SubModellBFksResourceServiceTest() {
        super(SubModellBFks.class, //
              path, //
              insertData, //
              updateData,
              stringField); //
    }
}
