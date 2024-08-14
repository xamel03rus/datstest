package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.enums.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("intermediateConverter")
public class IntermediateCurrencyConverterService implements CurrencyConverterService {
    @Autowired
    private ApplicationContext context;

    public CurrencyDto calculate(CurrencyConverterRequest request) {
        CurrencyParserService firstParser = getParser(request.currencyIn());
        CurrencyParserService secondParser = getParser(request.currencyIntermediate());

        CurrencyDto currencyDtoFirst = firstParser.parse(request.currencyIn(), request.currencyIntermediate());
        CurrencyDto currencyDtoSecond = secondParser.parse(request.currencyIntermediate(), request.currencyOut());

        currencyDtoFirst.setCurrencyOut(request.currencyOut());
        currencyDtoFirst.setValue(currencyDtoFirst.getValue() / currencyDtoSecond.getValue());
        currencyDtoFirst.setCount(request.count());
        currencyDtoFirst.setSum(request.currencyAction().equals(CurrencyActionEnum.BUY) ?
                request.count() * currencyDtoFirst.getValue() :
                request.count() / currencyDtoFirst.getValue());

        return currencyDtoFirst;
    }

    @Override
    public CurrencyParserService getParser(CurrencyEnum currency) {
        switch (currency) {
            case RUB -> {
                return (CurrencyParserService) context.getBean("ruParser");
            }
            case CNY -> {
                return (CurrencyParserService) context.getBean("cnyParser");
            }
            default -> throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Unavailable pair");
        }
    }
}