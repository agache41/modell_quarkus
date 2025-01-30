package modell.quarkus.scheduler;


import io.github.agache41.rest.contract.producer.Producer;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import modell.quarkus.entities.Modell;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class CounterBean {

    @Inject
    protected EntityManager em;

    private AtomicInteger counter = new AtomicInteger();
    private Producer<Modell> producer = Producer.ofClass(Modell.class)
                                                .withList(LinkedList::new)
                                                .withMap(LinkedHashMap::new)
                                                .withSize(16);

    public int get() {
        return counter.get();
    }

    @Transactional
    @Scheduled(every = "10s")
    void increment() {
        counter.incrementAndGet();
        System.out.println("Trigered!");

        Modell modell = producer.produce();
        em.persist(modell);
        Long count = em.createQuery("Select count(*) from Modell", Long.class)
                       .getSingleResult();

        System.out.println("Count = " + count);
    }

    @Scheduled(cron = "0 15 10 * * ?")
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

//    @Scheduled(cron = "{cron.expr}")
//    void cronJobWithExpressionInConfig() {
//        counter.incrementAndGet();
//        System.out.println("Cron expression configured in application.properties");
//    }
}