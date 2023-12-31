package modell.quarkus.paramConverter;

public class IntegerListParamConverter extends ListParamConvertor<Integer> {
    public IntegerListParamConverter() {
        super(Integer::parseInt);
    }
}