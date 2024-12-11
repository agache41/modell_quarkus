package modell.quarkus.contract.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface HeaderInfo {

    String name();
    boolean sortable() default false;
    String field() default "";
    String aggFunc() default "";
    String filter() default "";
    //String id();
}
