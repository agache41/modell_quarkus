package modell.quarkus.service;

import io.quarkus.test.junit.QuarkusTest;
import modell.quarkus.entities.Modell;

import java.util.Arrays;

@QuarkusTest
class ModellResourceTest extends AbstractResourceTest<Modell, Long> {

    static final String path = "/modell";
    static final Modell modell1 = new Modell(null, "m1");
    static final Modell modell2 = new Modell(null, "m2");
    static final Modell modell3 = new Modell(null, "m3");
    static final Modell modell4 = new Modell(null, "m4");
    static final Modell modell5 = new Modell(null, "m5");
    static final Modell modell1updated = new Modell(null, "m1updated");
    static final Modell modell2updated = new Modell(null, "m2updated");
    static final Modell modell3updated = new Modell(null, "m3updated");
    static final Modell modell4updated = new Modell(null, "m4updated");
    static final Modell modell5updated = new Modell(null, "m5updated");

    public ModellResourceTest() {
        super(Modell.class, //
                path, //
                Arrays.asList(modell1, modell2, modell3, modell4, modell5), //
                Arrays.asList(modell1updated, modell2updated, modell3updated, modell4updated, modell5updated)); //
    }
}
