package modell.quarkus.service;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.structured.api.quarkus.dao.PrimaryKey;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractResourceTest<T extends PrimaryKey<K>, K> {

    private final Class<T> clazz;
    private final String path;
    private final List<T> insertData;
    private final List<T> updateData;


    public AbstractResourceTest(Class<T> clazz, String path, List<T> insertData, List<T> updateData) {
        this.clazz = clazz;
        this.path = path;
        this.insertData = insertData;
        this.updateData = updateData;
        Assertions.assertEquals(insertData.size(), updateData.size(), " please use two lists of equal size ");
    }

    @BeforeAll
    public static void beforeAll() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Order(10)
    public void testPost() {
        for (int index = 0; index < insertData.size(); index++) {
            T req = this.insertData.get(index);
            Assertions.assertNotNull(req);
            Assertions.assertNull(req.getId());
            T res = given()
                    .contentType(ContentType.JSON)
                    .body(req)
                    .when()
                    .accept(ContentType.JSON)
                    .post(this.path)
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(this.clazz);
            Assertions.assertNotNull(res);

            K id = res.getId();
            Assertions.assertNotNull(id);

            req.setId(id);
            Assertions.assertEquals(req, res);

            T upd = this.updateData.get(index);
            Assertions.assertNotNull(upd);
            upd.setId(id);
        }
    }


    @Test
    @Order(20)
    public void testGet() {
        for (int index = 0; index < insertData.size(); index++) {
            T req = this.insertData.get(index);
            Assertions.assertNotNull(req);
            K id = req.getId();
            Assertions.assertNotNull(id);
            T res = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .get(this.path + "/{id}", id)
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(this.clazz);
            Assertions.assertNotNull(res);
            Assertions.assertNotNull(res.getId());
            Assertions.assertEquals(req.getId(), res.getId());
            Assertions.assertEquals(req, res);
        }
    }

    @Test
    @Order(21)
    public void testGetAll() {
        List<T> res = given()
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .get(this.path + "/all/asList")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(this.insertData.size(), res.size());
        Assertions.assertEquals(this.insertData, res);
    }

    @Test
    @Order(22)
    public void testGetByIdsAsList() {
        List<K> ids = this.insertData
                .stream()
                .map(PrimaryKey::getId)
                .collect(Collectors.toList());

        List<T> res = given()
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .get(this.path + "/byIds/{ids}/asList", ids)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(this.insertData.size(), res.size());
        Assertions.assertEquals(this.insertData, res);
    }

    @Test
    @Order(23)
    public void testPostByIdsAsList() {
        List<K> ids = this.insertData
                .stream()
                .map(PrimaryKey::getId)
                .collect(Collectors.toList());

        List<T> res = given()
                .contentType(ContentType.JSON)
                .body(ids)
                .when()
                .accept(ContentType.JSON)
                .post(this.path + "/byIds/asList")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(this.insertData.size(), res.size());
        Assertions.assertEquals(this.insertData, res);
    }


    @Test
    @Order(30)
    public void testPut() {
        for (int index = 0; index < updateData.size(); index++) {
            T req = this.updateData.get(index);
            Assertions.assertNotNull(req);
            K id = req.getId();
            Assertions.assertNotNull(id);
            T res = given()
                    .contentType(ContentType.JSON)
                    .body(req)
                    .when()
                    .accept(ContentType.JSON)
                    .put(this.path)
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(this.clazz);
            Assertions.assertNotNull(res);
            Assertions.assertNotNull(res.getId());
            Assertions.assertEquals(req, res);
        }
    }

    @Test
    @Order(40)
    public void testDelete() {
        for (int index = 0; index < updateData.size(); index++) {
            T req = this.updateData.get(index);
            Assertions.assertNotNull(req);
            K id = req.getId();
            Assertions.assertNotNull(id);
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .delete(this.path + "/{id}", id)
                    .then()
                    .statusCode(204);
        }
        Assertions.assertTrue(getAll().isEmpty());
    }

    protected List<T> getAll() {
        List<T> res = given()
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .get(this.path + "/all/asList")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        return res;
    }

    @Test
    @Order(50)
    public void testPostAll() {
        List<T> res = given()
                .contentType(ContentType.JSON)
                .body(this.insertData)
                .when()
                .accept(ContentType.JSON)
                .post(this.path + "/list/asList")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(this.insertData.size(), res.size());
        for (int index = 0; index < updateData.size(); index++) {
            this.insertData.get(index).setId(res.get(index).getId());
            this.updateData.get(index).setId(res.get(index).getId());
        }
        Assertions.assertEquals(this.insertData, res);
        Assertions.assertEquals(this.insertData, getAll());
    }

    @Test
    @Order(60)
    public void testPutAll() {
        List<T> res = given()
                .contentType(ContentType.JSON)
                .body(this.updateData)
                .when()
                .accept(ContentType.JSON)
                .put(this.path + "/list/asList")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", this.clazz);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(this.updateData.size(), res.size());
        Assertions.assertEquals(this.updateData, res);
        Assertions.assertEquals(this.updateData, getAll());
    }

    @Test
    @Order(70)
    public void testDeleteAll() {
        List<K> ids = getAll()
                .stream()
                .map(PrimaryKey::getId)
                .collect(Collectors.toList());
        Assertions.assertFalse(ids.isEmpty());
        given()
                .contentType(ContentType.JSON)
                .body(ids)
                .when()
                .accept(ContentType.JSON)
                .delete(this.path + "/list")
                .then()
                .statusCode(204);

        Assertions.assertTrue(getAll().isEmpty());
    }

//    @Test
//    @Order(22)
//    public void testGetFilter() {
//        List<K> ids = this.insertData
//                .stream()
//                .map(PrimaryKey::getId)
//                .collect(Collectors.toList());
//
//        List<T> res = given()
//                .contentType(ContentType.JSON)
//                .body(ids)
//                .when()
//                .accept(ContentType.JSON)
//                .post(this.path + "/get/list/asList")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .jsonPath()
//                .getList(".", this.clazz);
//        Assertions.assertNotNull(res);
//        Assertions.assertEquals(this.insertData.size(), res.size());
//        Assertions.assertEquals(this.insertData, res);
//    }
}
