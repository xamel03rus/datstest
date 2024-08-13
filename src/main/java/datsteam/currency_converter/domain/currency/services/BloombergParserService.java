package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.enums.CurrencyInEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyOutEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class BloombergParserService implements CurrencyParserService {
    private static final String baseUrl = "https://www.bloomberg.com/quote/%s%s:CUR";

    @Override
    public CurrencyDto parse(CurrencyInEnum currencyIn, CurrencyOutEnum currencyOut) {
        Document doc = Jsoup.parse(this.load(baseUrl.formatted(currencyIn, currencyOut)));

        Element currencyRate = doc.selectFirst("main div[data-component=sized-price]");

        if (currencyRate == null) {
            return null;
        }

        return new CurrencyDto(currencyIn, currencyOut, Float.parseFloat(currencyRate.text()));
    }
}