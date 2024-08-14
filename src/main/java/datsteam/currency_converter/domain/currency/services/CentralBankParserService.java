package datsteam.currency_converter.domain.currency.services;

import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;
import datsteam.currency_converter.domain.currency.interfaces.CurrencyParserService;
import datsteam.currency_converter.domain.currency.models.CurrencyDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service("ruParser")
public class CentralBankParserService implements CurrencyParserService {
    private static final String siteUrl = "http://www.cbr.ru/currency_base/daily/";

    @Override
    public CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut) {
        Document doc = Jsoup.parse(this.load(siteUrl));

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
