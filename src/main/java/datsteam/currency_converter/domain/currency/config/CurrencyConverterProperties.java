package datsteam.currency_converter.domain.currency.config;


import datsteam.currency_converter.domain.currency.model.CurrencyConvertRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "settings")
public class CurrencyConverterProperties {
    private List<CurrencyConvertRequestDto> conversions;
}
