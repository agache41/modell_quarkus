package modell.quarkus.temporary;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.structured.api.quarkus.dao.DataAccess;
import org.structured.api.quarkus.dao.PrimaryKey;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TempResource<T extends PrimaryKey<K>, K> {

    @Inject
    @Named("base")
    protected DataAccess<T, K> dataAccess;

    protected DataAccess<T, K> getDataAccess() {
        return dataAccess;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alls/{ids}")
    public List<T> getAll(@PathParam("ids") List<K> ids) {
        return getDataAccess().streamByIds(ids).collect(Collectors.toList());
    }


//    @Test
//    @Order(22)
//    public void testGetAllByIds2() {
//        List<K> ids = this.insertData
//                .stream()
//                .map(PrimaryKey::getId)
//                .collect(Collectors.toList());
//
//        List<T> res = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .accept(ContentType.JSON)
//                .get(this.path + "/alls/{ids}", ids)
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
