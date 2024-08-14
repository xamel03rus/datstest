package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.manager.CurrencyParserManager;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("directConverter")
@RequiredArgsConstructor
public class DirectCurrencyConverterService implements CurrencyConverterService {
    private final CurrencyParserManager parserManager;

    public CurrencyDto calculate(CurrencyConverterRequest request) {
        CurrencyParserService parserService = parserManager.getParser(request.currencyIn());

        CurrencyDto currencyDto = parserService.parse(request.currencyIn(), request.currencyOut());
        currencyDto.setCount(request.count());
        currencyDto.setSum(request.currencyAction().equals(CurrencyActionEnum.BUY) ?
                request.count() / currencyDto.getValue() :
                request.count() * currencyDto.getValue());

        return currencyDto;
    }
}