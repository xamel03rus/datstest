package datsteam.currency_converter.domain.currency.model;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class CurrencyDto {
    @NonNull
    private CurrencyEnum currencyIn;
    @NonNull
    private CurrencyEnum currencyOut;
    @NonNull
    private Float value;
    private Float count;
    private Float sum;
}
