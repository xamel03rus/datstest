package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("CNYParser")
@RequiredArgsConstructor
public class BloombergParserService implements CurrencyParserService {
    private static final String baseUrl = "https://www.bloomberg.com/quote/%s%s:CUR";
    private final WebClient webClient;

    @Override
    public CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut) {
        Document doc = Jsoup.parse(this.load(webClient, baseUrl.formatted(currencyIn, currencyOut)));

        Element currencyRate = doc.selectFirst("main div[data-component=sized-price]");

        if (currencyRate == null) {
            return null;
        }

        return new CurrencyDto(currencyIn, currencyOut, Float.parseFloat(currencyRate.text()));
    }
}