package modell.quarkus.grid.contract.classes.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.grid.contract.classes.request.filter.Operator;
import modell.quarkus.grid.contract.classes.request.filter.FilterType;

import java.util.List;

@Data
@NoArgsConstructor
public class CombinedSimpleModel {
    private FilterType filterType;
    private Operator operator;
    private List<CombinedSimpleModel> conditions;
    private String type;
    private String filter;

    private String filterTo;
    private String dateFrom;
    private String dateTo;
    private List<String> values;
}
