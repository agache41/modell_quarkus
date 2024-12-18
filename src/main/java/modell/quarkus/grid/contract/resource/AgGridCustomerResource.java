package modell.quarkus.grid.contract.resource;

import io.github.agache41.rest.contract.producer.Producer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.grid.contract.entitities.Customer;
import modell.quarkus.grid.contract.resource.dataAccess.AGGridDataAccess;
import org.jboss.logging.Logger;


@ApplicationScoped
@Path("/customer/ag-grid")
@Transactional
public class AgGridCustomerResource extends AbstractAgGridResource<Customer> {
    private static final Logger logger = Logger.getLogger(AgGridCustomerResource.class);

    @PostConstruct
    @Transactional
    void init() {
        Producer<Customer> customerProducer = Producer.ofClass(Customer.class);
        AGGridDataAccess em = this.dao();
        logger.info("Begin .... adding 20k Customers");
        for (int i = 0; i < 20137; i++) {
            em.persist(customerProducer.produce());
        }
        logger.info("Added 20k Customers");
    }
}
