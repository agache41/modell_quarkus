package modell.quarkus.grid.contract.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import modell.quarkus.grid.contract.classes.header.ColDef;
import modell.quarkus.grid.contract.classes.response.Rows;
import modell.quarkus.grid.contract.classes.request.GetRowsParam;
import modell.quarkus.grid.contract.resource.dataAccess.AGGridDataAccess;

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
    public Rows data(GetRowsParam request) {
        return this.dao().data(request);
    }
}
