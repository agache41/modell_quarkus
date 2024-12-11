package modell.quarkus.contract.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import modell.quarkus.contract.entitities.Customer;


@ApplicationScoped
@Path("/customer/ag-grid")
public class AgGridCustomerResource extends AgGridResource<Customer> {
    public AgGridCustomerResource() {
        super(Customer.class);
    }
}
