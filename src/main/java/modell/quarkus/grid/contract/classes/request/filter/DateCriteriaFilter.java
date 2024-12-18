package modell.quarkus.grid.contract.classes.request.filter;

import modell.quarkus.grid.contract.classes.request.CombinedSimpleModel;

public class DateCriteriaFilter<FIELD extends Comparable<FIELD>> extends CriteriaFilter<FIELD> {

    @Override
    protected String filter(CombinedSimpleModel columnfilter) {
        return columnfilter.getDateFrom();
    }

    @Override
    protected String filterTo(CombinedSimpleModel columnfilter) {
        return columnfilter.getDateTo();
    }
}
