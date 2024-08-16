package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyConverterService;
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
        CurrencyDto currencyDto = parserManager
                .getParser(request.currencyIn())
                .parse(request.currencyIn(), request.currencyOut()
        );
        currencyDto.setCount(request.count());

        Float sum;
        if (request.currencyAction().equals(CurrencyActionEnum.BUY)) {
            sum = request.count() * currencyDto.getValue();
        } else {
            sum = request.count() / currencyDto.getValue();
        }
        currencyDto.setSum(sum);

        return currencyDto;
    }
}