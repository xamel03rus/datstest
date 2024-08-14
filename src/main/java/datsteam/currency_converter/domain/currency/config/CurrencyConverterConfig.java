package datsteam.currency_converter.domain.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConverterConfig {

    @Bean
    public CurrencyConverterProperties conversionProperties()
    {
        return new CurrencyConverterProperties();
    }
}