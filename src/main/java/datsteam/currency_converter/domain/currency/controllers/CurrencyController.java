package datsteam.currency_converter.domain.currency.controllers;

import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import datsteam.currency_converter.domain.currency.requests.CurrencyConverterRequest;
import datsteam.currency_converter.domain.currency.services.CurrencyConverterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    @PostMapping("/")
    public CurrencyDto convert(@Valid @RequestBody CurrencyConverterRequest request)
    {
        return new CurrencyConverterService().calculate(request);
    }
}
