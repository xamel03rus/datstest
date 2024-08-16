package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.manager.CurrencyParserManager;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("intermediateConverter")
@RequiredArgsConstructor
public class IntermediateCurrencyConverterService implements CurrencyConverterService {
    private final CurrencyParserManager parserManager;

    public CurrencyDto calculate(CurrencyConverterRequest request) {
        CurrencyParserService firstParser = parserManager.getParser(request.currencyIn());
        CurrencyParserService secondParser = parserManager.getParser(request.currencyIntermediate());

        CurrencyDto currencyDtoFirst = firstParser.parse(request.currencyIn(), request.currencyIntermediate());
        CurrencyDto currencyDtoSecond = secondParser.parse(request.currencyIntermediate(), request.currencyOut());

        currencyDtoFirst.setCurrencyOut(request.currencyOut());
        currencyDtoFirst.setValue(currencyDtoFirst.getValue() / currencyDtoSecond.getValue());
        currencyDtoFirst.setCount(request.count());

        Float sum;
        if (request.currencyAction().equals(CurrencyActionEnum.BUY)) {
            sum = request.count() / currencyDtoFirst.getValue();
        } else {
            sum = request.count() * currencyDtoFirst.getValue();
        }
        currencyDtoFirst.setSum(sum);

        return currencyDtoFirst;
    }
}