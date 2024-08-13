package datsteam.currency_converter.domain.currency.requests;

import datsteam.currency_converter.domain.currency.enums.CurrencyInEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyOutEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CurrencyConverterRequest(
        @NotNull
        CurrencyInEnum currencyIn,
        @NotNull
        CurrencyOutEnum currencyOut,
        @DecimalMin("0.01")
        Float count
) {
}
