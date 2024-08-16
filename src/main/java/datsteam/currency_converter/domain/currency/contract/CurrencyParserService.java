package datsteam.currency_converter.domain.currency.contract;

import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;

public interface CurrencyParserService {
    @CacheEvict(value = "converterCache", allEntries = true)
    @Scheduled(fixedRateString = "${settings.caching.ttl}")
    default void cacheClear()
    {
    }

    @Cacheable("converterCache")
    CurrencyDto parse(CurrencyEnum currencyIn, CurrencyEnum currencyOut);
}
