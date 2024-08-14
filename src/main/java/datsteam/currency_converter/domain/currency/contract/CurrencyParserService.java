package datsteam.currency_converter.domain.currency.contract;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import org.springframework.web.reactive.function.client.WebClient;

public interface CurrencyParserService {
    default String load(WebClient webClient, String url) {
        return webClient
                .get().uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut);
}
