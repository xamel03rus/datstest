package datsteam.currency_converter.domain.currency.manager;

import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyParserManager {
    @Autowired
    private Map<String, CurrencyParserService> parsers;

    public CurrencyParserService getParser(CurrencyEnum currency) {
        if (!parsers.containsKey(currency + "Parser")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Unavailable pair");
        }

        return parsers.get(currency + "Parser");
    }
}
