package datsteam.currency_converter.domain.currency.manager;

import datsteam.currency_converter.domain.currency.config.CurrencyConverterProperties;
import datsteam.currency_converter.domain.currency.contract.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.model.CurrencyConvertRequestDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CurrencyConverterManager {
    private final ApplicationContext applicationContext;
    private final CurrencyConverterProperties config;

    public CurrencyConverterService getConverter(CurrencyConverterRequest request) {
        CurrencyConvertRequestDto currentRequest = new CurrencyConvertRequestDto(
                request.currencyIn(), request.currencyIntermediate(), request.currencyOut()
        );

        if (!config.getConversions().contains(currentRequest)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Unavailable pair");
        }

        return (CurrencyConverterService) applicationContext.getBean(
                Objects.isNull(request.currencyIntermediate()) ? "directConverter" : "intermediateConverter"
        );
    }
}
