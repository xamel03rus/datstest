package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.enums.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("directConverter")
public class DirectCurrencyConverterService implements CurrencyConverterService {

    @Autowired
    private ApplicationContext context;

    public CurrencyDto calculate(CurrencyConverterRequest request) {
        CurrencyParserService parserService = this.getParser(request.currencyIn());

        CurrencyDto currencyDto = parserService.parse(request.currencyIn(), request.currencyOut());
        currencyDto.setCount(request.count());
        currencyDto.setSum(request.currencyAction().equals(CurrencyActionEnum.BUY) ?
                request.count() / currencyDto.getValue() :
                request.count() * currencyDto.getValue());

        return currencyDto;
    }

    @Override
    public CurrencyParserService getParser(CurrencyEnum currency)
    {
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