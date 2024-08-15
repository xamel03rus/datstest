package datsteam.currency_converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyActionEnum;
import datsteam.currency_converter.domain.currency.enumeration.CurrencyEnum;
import datsteam.currency_converter.domain.currency.request.CurrencyConverterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConverterTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBadRequestNoRequiredFields() throws Exception {
        this.mockMvc.perform(post("/convert")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnMimimalCount() throws Exception {
        CurrencyConverterRequest request = new CurrencyConverterRequest(CurrencyEnum.USD, CurrencyEnum.EUR, CurrencyEnum.USD, 0f, CurrencyActionEnum.BUY);

        this.mockMvc.perform(post("/convert")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnUnavailablePairs() throws Exception {
        CurrencyConverterRequest request = new CurrencyConverterRequest(CurrencyEnum.USD, CurrencyEnum.EUR, CurrencyEnum.USD, 1f, CurrencyActionEnum.BUY);

        this.mockMvc.perform(post("/convert")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound());
    }
}
