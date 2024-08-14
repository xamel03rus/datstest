package datsteam.currency_converter.domain.currency.controller;

import datsteam.currency_converter.domain.currency.manager.CurrencyConverterManager;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyConverterManager converterManager;

    @PostMapping("/convert")
    public CurrencyDto convert(@Valid @RequestBody CurrencyConverterRequest request) {
        return converterManager.getConverter(request).calculate(request);
    }
}
