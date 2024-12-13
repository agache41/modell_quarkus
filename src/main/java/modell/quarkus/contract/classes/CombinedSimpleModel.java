package modell.quarkus.contract.classes;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CombinedSimpleModel {

    private String colId;
    private String filterType;
    private String type;
    private String filter;
    private String filterTo;
    private String dateFrom;
    private String dateTo;
    private List<String> values;
}
