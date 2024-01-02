package modell.quarkus.service;

import io.quarkus.test.junit.QuarkusTest;
import modell.quarkus.entities.Modell;
import org.structured.api.quarkus.resourceService.AbstractResourceServiceImplTest;

import java.util.Arrays;

@QuarkusTest
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

    static final String path = "/modell";
    static final Modell modell1 = Modell.builder()
                                        .name("aaaa")
                                        .street("one")
                                        .no(1)
                                        .build();
    static final Modell modell2 = Modell.builder()
                                        .name("aaaab")
                                        .street("two")
                                        .no(2)
                                        .build();
    static final Modell modell3 = Modell.builder()
                                        .name("aaaabc")
                                        .street("three")
                                        .no(3)
                                        .build();
    static final Modell modell4 = Modell.builder()
                                        .name("aaaabcd")
                                        .street("four")
                                        .no(null)
                                        .build();
    static final Modell modell5 = Modell.builder()
                                        .name("aaaabcde")
                                        .street(null)
                                        .no(5)
                                        .build();
    static final Modell modell1updated = Modell.builder()
                                               .name("aaaa updated")
                                               .street(null)
                                               .no(11)
                                               .build();
    static final Modell modell2updated = Modell.builder()
                                               .name("aaaab updated")
                                               .street("two updated")
                                               .no(12)
                                               .build();
    static final Modell modell3updated = Modell.builder()
                                               .name("aaaabc updated")
                                               .street("three updated")
                                               .no(null)
                                               .build();
    static final Modell modell4updated = Modell.builder()
                                               .name("aaaaabcd updated")
                                               .street("four updated")
                                               .no(14)
                                               .build();
    static final Modell modell5updated = Modell.builder()
                                               .name("aaaaabcdd updated")
                                               .street(null)
                                               .no(15)
                                               .build();

    static final String stringField = "name";

    public ModellResourceServiceTest() {
        super(Modell.class, //
                path, //
                Arrays.asList(modell1, modell2, modell3, modell4, modell5), //
                Arrays.asList(modell1updated, modell2updated, modell3updated, modell4updated, modell5updated),
                stringField); //
    }
}
