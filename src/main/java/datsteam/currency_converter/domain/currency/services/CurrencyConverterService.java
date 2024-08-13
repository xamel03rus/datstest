package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {
    private CurrencyParserService parserService;

    public CurrencyDto calculate(CurrencyConverterRequest request)
    {
        CurrencyDto currencyDto = this.parserService.parse(request.currencyIn(), request.currencyOut());
        currencyDto.setCount(request.count());
        currencyDto.setSum(request.count() * currencyDto.getValue());

        return currencyDto;
    }
}