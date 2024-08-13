package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.enums.CurrencyInEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyOutEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import org.springframework.stereotype.Service;

@Service
public class IntermediateCurrencyParserService implements CurrencyParserService {
    private CurrencyParserService firstParser;
    private CurrencyParserService secondParser;
    private CurrencyInEnum intermediateCurrencyIn;
    private CurrencyOutEnum intermediateCurrencyOut;

    @Override
    public CurrencyDto parse(CurrencyInEnum currencyIn, CurrencyOutEnum currencyOut) {
        CurrencyDto currencyDtoFirst = firstParser.parse(currencyIn, intermediateCurrencyOut);
        CurrencyDto currencyDtoSecond = secondParser.parse(intermediateCurrencyIn, currencyOut);

        currencyDtoFirst.setCurrencyOut(currencyOut);
        currencyDtoFirst.setValue(currencyDtoFirst.getValue() * currencyDtoSecond.getValue());

        return currencyDtoFirst;
    }
}