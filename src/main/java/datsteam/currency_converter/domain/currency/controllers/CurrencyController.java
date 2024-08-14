package datsteam.currency_converter.domain.currency.controllers;

import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.models.CurrencyConvertRequestDto;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyController {
    private static Map<CurrencyConvertRequestDto, String> availableConversion;

    @Autowired
    private ApplicationContext applicationContext;

    static {
        availableConversion = new HashMap();
        availableConversion.put(new CurrencyConvertRequestDto(CurrencyEnum.RUB, CurrencyEnum.CNY, CurrencyEnum.USD), "intermediateConverter");
        availableConversion.put(new CurrencyConvertRequestDto(CurrencyEnum.RUB, CurrencyEnum.CNY, CurrencyEnum.EUR), "intermediateConverter");
        availableConversion.put(new CurrencyConvertRequestDto(CurrencyEnum.CNY, null, CurrencyEnum.EUR), "directConverter");
        availableConversion.put(new CurrencyConvertRequestDto(CurrencyEnum.CNY, null, CurrencyEnum.USD), "directConverter");
    }

    @PostMapping("/")
    public CurrencyDto convert(@Valid @RequestBody CurrencyConverterRequest request) {
        return getConverterService(request).calculate(request);
    }

    private CurrencyConverterService getConverterService(CurrencyConverterRequest request) {
        CurrencyConvertRequestDto currentRequest = new CurrencyConvertRequestDto(
                request.currencyIn(), request.currencyIntermediate(), request.currencyOut()
        );

        String s = availableConversion.get(currentRequest);
        if (s == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Unavailable pair");
        }

        System.out.println("Bean " + s);

        return (CurrencyConverterService)applicationContext.getBean(s);
    }
}
