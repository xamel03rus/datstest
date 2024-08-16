package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.exception.PairParseException;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("RUBParser")
@RequiredArgsConstructor
public class CentralBankParserService implements CurrencyParserService {
    private static final String SITE_URL = "http://www.cbr.ru/currency_base/daily/";
    private final WebClientService webClientService;

    @Override
    public CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut) {
        String rateText = Jsoup.parse(webClientService.get(SITE_URL).block())
                .select("table tr").stream()
                .filter(tr -> !Objects.isNull(tr.selectFirst("td:contains(%s)".formatted(currencyOut.toString()))))
                .findFirst()
                .map(el -> el.select("td").last().text())
                .orElseThrow(PairParseException::new);

        return new CurrencyDto(
                currencyIn,
                currencyOut,
                Float.parseFloat(rateText.replace(',', '.'))
        );
    }
}
