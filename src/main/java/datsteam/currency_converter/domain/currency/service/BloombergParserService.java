package datsteam.currency_converter.domain.currency.service;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.exception.PairParseException;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service("CNYParser")
@RequiredArgsConstructor
public class BloombergParserService implements CurrencyParserService {
    private static final String SITE_URL = "https://www.bloomberg.com/quote/%s%s:CUR";
    private final WebClientService webClientService;

    @Override
    public CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut) {
        String currencyRate = Jsoup.parse(
                        webClientService.get(SITE_URL.formatted(currencyIn, currencyOut)).block()
                ).select("main div[data-component=sized-price]")
                .stream().findFirst().map(Element::text).orElseThrow(PairParseException::new);

        return new CurrencyDto(currencyIn, currencyOut, Float.parseFloat(currencyRate));
    }
}