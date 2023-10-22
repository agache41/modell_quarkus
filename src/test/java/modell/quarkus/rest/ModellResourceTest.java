package modell.quarkus.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import modell.quarkus.entities.Modell;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModellResourceTest {

    private static Long id;
    private final Modell modellPOST = new Modell(null, "post");
    private final Modell modellPUT = new Modell(null, "put");

    @BeforeAll
    public static void beforeAll() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Order(10)
    public void testPost() {
        Modell modellResponse = given()
                .contentType(ContentType.JSON)
                .body(modellPOST)
                .when()
                .accept(ContentType.JSON)
                .post("/modell")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Modell.class);
        Assertions.assertNotNull(modellResponse);
        Assertions.assertEquals(modellPOST.getStringValue(), modellResponse.getStringValue());
        id = modellResponse.getId();
        Assertions.assertNotNull(id);
    }


    @Test
    @Order(20)
    public void testGet() {
        Modell modellResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .get("/modell/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Modell.class);
        Assertions.assertNotNull(modellResponse);
        Assertions.assertEquals(modellPOST.getStringValue(), modellResponse.getStringValue());
        Assertions.assertEquals(id, modellResponse.getId());
    }


    @Test
    @Order(30)
    public void testPut() {
        modellPUT.setId(id);
        Modell modellResponse = given()
                .contentType(ContentType.JSON)
                .body(modellPUT)
                .when()
                .accept(ContentType.JSON)
                .put("/modell")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Modell.class);
        Assertions.assertNotNull(modellResponse);
        Assertions.assertEquals(modellPUT, modellResponse);
    }

    @Test
    @Order(40)
    public void testDelete() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .delete("/modell/{id}", id)
                .then()
                .statusCode(204);
    }

}
