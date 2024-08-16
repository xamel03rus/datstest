package datsteam.currency_converter.domain.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final int BUFFER_SIZE = 32 * 1024 * 1024;

    @Bean
    public WebClient webClient()
    {
        return WebClient.builder().codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(BUFFER_SIZE)).build();
    }
}
