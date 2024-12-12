package modell.quarkus.contract.resource;

import io.github.agache41.rest.contract.producer.Producer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.contract.entitities.Customer;
import modell.quarkus.contract.resource.dataAccess.AGGridDataAccess;


@ApplicationScoped
@Path("/customer/ag-grid")
@Transactional
public class AgGridCustomerResource extends AbstractAgGridResource<Customer> {

    @PostConstruct
    @Transactional
    void init() {
        Producer<Customer> customerProducer = Producer.ofClass(Customer.class);
        AGGridDataAccess em = this.dao();

        for (int i = 0; i < 100000; i++) {
            em.persist(customerProducer.produce());
        }
    }
}
