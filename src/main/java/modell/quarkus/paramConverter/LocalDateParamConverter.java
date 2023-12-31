package modell.quarkus.paramConverter;

import java.time.LocalDate;

public class LocalDateParamConverter extends ListParamConvertor<LocalDate> {
    public LocalDateParamConverter() {
        super(LocalDate::parse);
    }
}