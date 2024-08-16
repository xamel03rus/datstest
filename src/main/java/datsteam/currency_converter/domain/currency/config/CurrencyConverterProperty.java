package datsteam.currency_converter.domain.currency.config;


import datsteam.currency_converter.domain.currency.model.CurrencyConvertRequestDto;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "settings")
public record CurrencyConverterProperty(List<CurrencyConvertRequestDto> conversions) {
}
