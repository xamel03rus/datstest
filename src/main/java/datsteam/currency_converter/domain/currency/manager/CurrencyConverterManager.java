package datsteam.currency_converter.domain.currency.manager;

import datsteam.currency_converter.domain.currency.config.CurrencyConverterProperty;
import datsteam.currency_converter.domain.currency.contract.CurrencyConverterService;
import datsteam.currency_converter.domain.currency.exception.UnAvailablePairException;
import datsteam.currency_converter.domain.currency.model.CurrencyConvertRequestDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyConverterManager {
    private final Map<String, CurrencyConverterService> converters;
    private final CurrencyConverterProperty config;

    public CurrencyConverterService getConverter(CurrencyConverterRequest request) {
        CurrencyConvertRequestDto currentRequest = new CurrencyConvertRequestDto(
                request.currencyIn(), request.currencyIntermediate(), request.currencyOut()
        );

        config.conversions().stream()
                .filter(c -> c.equals(currentRequest))
                .findFirst().orElseThrow(UnAvailablePairException::new);

        var converterName = "intermediateConverter";
        if (Objects.isNull(request.currencyIntermediate())) {
            converterName = "directConverter";
        }

        return Optional.of(converters.get(converterName)).orElseThrow(UnAvailablePairException::new);
    }
}
