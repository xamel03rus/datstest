package datsteam.currency_converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.model.CurrencyDto;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConverterTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @Test
    void shouldReturnBadRequestNoRequiredFields() throws Exception {
        this.mockMvc.perform(post("/convert")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnMimimalCount() throws Exception {
        CurrencyConverterRequest request = new CurrencyConverterRequest(CurrencyEnum.USD, CurrencyEnum.EUR, CurrencyEnum.USD, 0f, CurrencyActionEnum.BUY);

        this.mockMvc.perform(post("/convert")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnUnavailablePairs() throws Exception {
        CurrencyConverterRequest request = new CurrencyConverterRequest(CurrencyEnum.USD, CurrencyEnum.EUR, CurrencyEnum.USD, 1f, CurrencyActionEnum.BUY);

        this.mockMvc.perform(post("/convert")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void directCentralBankParseExpectedOk() throws Exception {
        Float expectRate = 0.5f;
        Float count = 1f;

        CurrencyEnum in = CurrencyEnum.RUB;
        CurrencyEnum out = CurrencyEnum.CNY;

        CurrencyConverterRequest request = new CurrencyConverterRequest(in, null, out, count, CurrencyActionEnum.BUY);

        CurrencyDto responseDto = new CurrencyDto(in, out, expectRate);
        responseDto.setCount(count);
        responseDto.setValue(expectRate);
        responseDto.setSum(count * expectRate);

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("http://www.cbr.ru/currency_base/daily/")).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(String.class)).thenReturn(
                Mono.just("<html><table><tr><td>%s</td><td>%f</td></tr></table></html>".formatted(out, expectRate))
        );

        this.mockMvc.perform(post("/convert")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(responseDto)));
    }

    @Test
    void directBloombergParseExpectedOk() throws Exception {
        Float expectRate = 0.5f;
        Float count = 1f;

        CurrencyEnum in = CurrencyEnum.CNY;
        CurrencyEnum out = CurrencyEnum.USD;

        CurrencyConverterRequest request = new CurrencyConverterRequest(in, null, out, count, CurrencyActionEnum.BUY);

        CurrencyDto responseDto = new CurrencyDto(in, out, expectRate);
        responseDto.setCount(count);
        responseDto.setValue(expectRate);
        responseDto.setSum(count * expectRate);

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri("https://www.bloomberg.com/quote/%s%s:CUR".formatted(in, out))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(String.class)).thenReturn(
                Mono.just("<html><main><div data-component=sized-price>%s</div></main></html>".formatted(expectRate))
        );

        this.mockMvc.perform(post("/convert")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(responseDto)));
    }
}
