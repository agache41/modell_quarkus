package modell.quarkus.contract.interfaces;

public enum FilterType {
    text(new TextCriteriaFilter()),
    number(new NumberCriteriaFilter()),
    date(new DateCriteriaFilter());

    private final ICriteriaFilter criteriaFilter;


    FilterType(ICriteriaFilter criteriaFilter) {
        this.criteriaFilter = criteriaFilter;
    }

    public ICriteriaFilter criteriaFilter() {
        return this.criteriaFilter;
    }
}
