package datsteam.currency_converter.domain.currency.manager;

import datsteam.currency_converter.domain.currency.contract.CurrencyParserService;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.exception.UnAvailablePairException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyParserManager {
    @Autowired
    private Map<String, CurrencyParserService> parsers;

    public CurrencyParserService getParser(CurrencyEnum currency) {
        return Optional.of(parsers.get(currency + "Parser"))
                .orElseThrow(UnAvailablePairException::new);
    }
}
