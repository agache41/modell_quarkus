package modell.quarkus.grid.contract.classes.request.sort;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;

import java.util.function.BiFunction;

public enum Sort {
    asc(CriteriaBuilder::asc), //
    desc(CriteriaBuilder::desc); //

    private final BiFunction<CriteriaBuilder, Expression<?>, Order> function;

    Sort(BiFunction<CriteriaBuilder, Expression<?>, Order> function) {
        this.function = function;
    }

    public BiFunction<CriteriaBuilder, Expression<?>, Order> function() {
        return function;
    }
}
