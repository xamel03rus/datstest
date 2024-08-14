package datsteam.currency_converter.domain.currency.model;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;

public record CurrencyConvertRequestDto(
        CurrencyEnum currencyIn,
        CurrencyEnum currencyIntermediate,
        CurrencyEnum currencyOut
) {
}
