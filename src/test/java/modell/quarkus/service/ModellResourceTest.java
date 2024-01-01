package modell.quarkus.service;

import io.quarkus.test.junit.QuarkusTest;
import modell.quarkus.entities.Modell;

import java.util.Arrays;

@QuarkusTest
class ModellResourceTest extends AbstractResourceTest<Modell, Long> {

    static final String path = "/modell";
    static final Modell modell1 = Modell.builder().name("a").street("one").no(1).build();
    static final Modell modell2 = Modell.builder().name("ab").street("two").no(2).build();
    static final Modell modell3 = Modell.builder().name("abc").street("three").no(3).build();
    static final Modell modell4 = Modell.builder().name("abcd").street("four").no(null).build();
    static final Modell modell5 = Modell.builder().name("abcde").street(null).no(5).build();
    static final Modell modell1updated = Modell.builder().name("a updated").street(null).no(11).build();
    static final Modell modell2updated = Modell.builder().name("ab updated").street("two updated").no(12).build();
    static final Modell modell3updated = Modell.builder().name("abc updated").street("three updated").no(null).build();
    static final Modell modell4updated = Modell.builder().name("abcd updated").street("four updated").no(14).build();
    static final Modell modell5updated = Modell.builder().name("abcdd updated").street(null).no(15).build();

    static final String stringField = "name";

    public ModellResourceTest() {
        super(Modell.class, //
                path, //
                Arrays.asList(modell1, modell2, modell3, modell4, modell5), //
                Arrays.asList(modell1updated, modell2updated, modell3updated, modell4updated, modell5updated),
                stringField); //
    }
}
