package datsteam.currency_converter.domain.currency.interfaces;

import datsteam.currency_converter.domain.currency.enums.CurrencyInEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyOutEnum;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import org.springframework.web.reactive.function.client.WebClient;

public interface CurrencyParserService {
    default String load(String url)
    {
        WebClient client = WebClient.builder().codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
                .baseUrl(url).build();

        WebClient.ResponseSpec response = client.get().retrieve();
        return response.bodyToMono(String.class).block();
    }

    CurrencyDto parse(CurrencyInEnum currencyIn, CurrencyOutEnum currencyOut);
}
