package modell.quarkus.grid.contract.classes.request.filter;

public enum FilterType {
    text(new TextCriteriaFilter()),
    number(new NumberCriteriaFilter<>()),
    date(new DateCriteriaFilter<>());

    private final ICriteriaFilter<?> criteriaFilter;

    FilterType(ICriteriaFilter<?> criteriaFilter) {
        this.criteriaFilter = criteriaFilter;
    }

    public ICriteriaFilter criteriaFilter() {
        return this.criteriaFilter;
    }
}
