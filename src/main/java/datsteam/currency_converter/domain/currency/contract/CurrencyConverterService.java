package datsteam.currency_converter.domain.currency.contract;

import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;

public interface CurrencyConverterService {
    CurrencyDto calculate(CurrencyConverterRequest request);
}
