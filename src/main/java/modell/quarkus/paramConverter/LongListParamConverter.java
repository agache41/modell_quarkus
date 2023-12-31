package modell.quarkus.paramConverter;

public class LongListParamConverter extends ListParamConvertor<Long> {
    public LongListParamConverter() {
        super(Long::parseLong);
    }
}