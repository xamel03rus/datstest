package datsteam.currency_converter.domain.currency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    public Mono<String> get(String url)
    {
        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
}
