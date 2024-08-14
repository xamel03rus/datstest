package datsteam.currency_converter.domain.currency.interfaces;

import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;

public interface CurrencyConverterService {
    CurrencyDto calculate(CurrencyConverterRequest request);

    CurrencyParserService getParser(CurrencyEnum currency);
}
