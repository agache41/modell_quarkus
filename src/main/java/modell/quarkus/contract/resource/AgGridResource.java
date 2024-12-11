package modell.quarkus.contract.resource;


import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.accessor.PositionComparator;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.HaveAnnotation;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import modell.quarkus.contract.classes.ColDef;
import modell.quarkus.contract.interfaces.HeaderInfo;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public class AgGridResource<ENTITY> {

    private final Class<ENTITY> entityClass;
    private List<ColDef<ENTITY, ?>> header;

    public AgGridResource(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
        this.header = Annotator
                .of(this.entityClass)
                .getAccessorsThat(HaveAnnotation.ofType(HeaderInfo.class))
                .sorted(new PositionComparator())
                .map(accessor -> new ColDef<ENTITY, Object>(accessor.getName(), accessor.getAnnotation(HeaderInfo.class, true)))
                .collect(Collectors.toList());
    }

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
    public AgGridResource(final InjectionPoint ip) {
        this((Class<ENTITY>) ((ParameterizedType) ip.getType()).getActualTypeArguments()[0]);//
    }


    @GET
    @Produces({"application/json"})
    @Path("/header")
    public List<ColDef<ENTITY, ?>> header() {
        return this.header;
    }
}
