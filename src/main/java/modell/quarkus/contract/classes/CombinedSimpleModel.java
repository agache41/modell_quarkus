package modell.quarkus.contract.classes;

import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.contract.interfaces.FilterType;
import modell.quarkus.contract.interfaces.Operator;

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
