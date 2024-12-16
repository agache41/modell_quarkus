package modell.quarkus.contract.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import modell.quarkus.contract.classes.CombinedSimpleModel;

public interface ICriteriaFilter {
    <ENTITY> Predicate predicate(
            CriteriaBuilder cb,
            Root<ENTITY> root,
            String field,
            CombinedSimpleModel columnfilter,
            Object[] parameters);
}
