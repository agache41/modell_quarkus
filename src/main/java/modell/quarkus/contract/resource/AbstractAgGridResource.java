package modell.quarkus.contract.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import modell.quarkus.contract.classes.ColDef;
import modell.quarkus.contract.classes.EnterpriseGetRowsResponse;
import modell.quarkus.contract.classes.ServerSideGetRowsRequest;
import modell.quarkus.contract.resource.dataAccess.AGGridDataAccess;

import java.util.List;

public abstract class AbstractAgGridResource<ENTITY> {

    @Inject
    private AGGridDataAccess<ENTITY> dao;

    protected AGGridDataAccess<ENTITY> dao(){
        return this.dao;
    }

    @GET
    @Produces({"application/json"})
    @Path("/header")
    public List<ColDef<ENTITY, ?>> header() {
        return this.dao().getHeader();
    }


    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/data")
    public EnterpriseGetRowsResponse data(ServerSideGetRowsRequest request) {
        return this.dao().data(request);
    }
}
