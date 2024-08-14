package datsteam.currency_converter.domain.currency.models;

import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;

public record CurrencyConvertRequestDto(
        CurrencyEnum currencyIn,
        CurrencyEnum currencyIntermediate,
        CurrencyEnum currencyOut
) {
}
