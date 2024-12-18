package modell.quarkus.grid.contract.classes.request.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

import java.util.function.BiFunction;


public enum Operator {
    AND(CriteriaBuilder::and),
    OR(CriteriaBuilder::or);

    private final BiFunction<CriteriaBuilder, Predicate[], Predicate> function;

    Operator(BiFunction<CriteriaBuilder, Predicate[], Predicate> function) {
        this.function = function;
    }

    public BiFunction<CriteriaBuilder, Predicate[], Predicate> function() {
        return this.function;
    }
}
