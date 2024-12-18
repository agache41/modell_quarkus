package modell.quarkus.grid.contract.classes.header;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterParams {
    Boolean applyMiniFilterWhileTyping;// = true;
    List<String> filterValues;// = Arrays.asList("one", "two", "three");
    Boolean caseSensitive;// = false;
}
