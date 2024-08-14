package datsteam.currency_converter.domain.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final int bufferSize = 32 * 1024 * 1024;

    @Bean
    public WebClient getWebClient()
    {
        return WebClient.builder().codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(bufferSize)).build();
    }
}
