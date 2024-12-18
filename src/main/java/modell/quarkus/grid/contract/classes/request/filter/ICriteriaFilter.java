package modell.quarkus.grid.contract.classes.request.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.grid.contract.classes.request.CombinedSimpleModel;

public interface ICriteriaFilter<FIELD extends Comparable<FIELD>> {
    <ENTITY> Predicate predicate(
            CriteriaBuilder cb,
            Root<ENTITY> root,
            Expression<FIELD> value,
            Class<FIELD> fieldType,
            CombinedSimpleModel columnFilter,
            Object[] parameters);
}
