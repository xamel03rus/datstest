package datsteam.currency_converter.domain.currency.requests;

import datsteam.currency_converter.domain.currency.enums.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CurrencyConverterRequest(
        @NotNull
        CurrencyEnum currencyIn,
        CurrencyEnum currencyIntermediate,
        @NotNull
        CurrencyEnum currencyOut,
        @DecimalMin("0.01")
        Float count,
        @NotNull
        CurrencyActionEnum currencyAction
) {
}
