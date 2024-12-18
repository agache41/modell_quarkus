package modell.quarkus.grid.contract.classes.request.filter;

import modell.quarkus.grid.contract.classes.request.CombinedSimpleModel;

public class NumberCriteriaFilter<FIELD extends Comparable<FIELD>> extends CriteriaFilter<FIELD> {

    @Override
    protected String filter(CombinedSimpleModel columnFilter) {
        return columnFilter.getFilter();
    }

    @Override
    protected String filterTo(CombinedSimpleModel columnFilter) {
        return columnFilter.getFilterTo();
    }
}
