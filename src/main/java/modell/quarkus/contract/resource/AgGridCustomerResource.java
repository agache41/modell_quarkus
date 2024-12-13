package modell.quarkus.contract.resource;

import io.github.agache41.rest.contract.producer.Producer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import modell.quarkus.contract.entitities.Customer;
import modell.quarkus.contract.resource.dataAccess.AGGridDataAccess;
import modell.quarkus.dao.DurationLogger;
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
        logger.info("Begin .... adding 100k Customers");
        for (int i = 0; i < 100000; i++) {
            em.persist(customerProducer.produce());
        }
        logger.info("Added 100k Customers");
    }
}
