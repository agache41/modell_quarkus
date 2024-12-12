package modell.quarkus.contract.resource.dataAccess;


import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import modell.quarkus.contract.resource.abstrc.AbstractAGGridDataAccess;

import java.lang.reflect.ParameterizedType;

@Dependent
@Transactional
public class AGGridDataAccess<ENTITY> extends AbstractAGGridDataAccess<ENTITY> {

    @Inject
    private EntityManager em;

    /**
     * <pre>
     * Injection Point Constructor
     * Example how to inject a AgGridResource for a class TypeClass with Primary Key PKey:
     * &#064;Inject DataAccess &#x3C;MyClass, PKey&#x3E; myClassDao;
     * </pre>
     *
     * @param ip the underlining injection point, provided by CDI.
     */
    @Inject
    public AGGridDataAccess(final InjectionPoint ip) {
        super((Class<ENTITY>) ((ParameterizedType) ip.getType()).getActualTypeArguments()[0]);//
    }



    public EntityManager em(){
        return this.em;
    }

    public void persist(ENTITY entity){
        this.em.persist(entity);
    }
}
