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

@Service("RUBParser")
@RequiredArgsConstructor
public class CentralBankParserService implements CurrencyParserService {
    private static final String siteUrl = "http://www.cbr.ru/currency_base/daily/";
    private final WebClient webClient;

    @Override
    public CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut) {
        Document doc = Jsoup.parse(this.load(webClient, siteUrl));

        Element targetLine = doc.select("table tr").stream()
                .filter(tr -> tr.selectFirst("td:contains(%s)".formatted(currencyOut.toString())) != null)
                .findFirst()
                .orElse(null);

        if (targetLine == null) {
            return null;
        }

        return new CurrencyDto(
                currencyIn,
                currencyOut,
                Float.parseFloat(
                        targetLine.select("td").last().text().replace(',', '.')
                )
        );
    }
}
