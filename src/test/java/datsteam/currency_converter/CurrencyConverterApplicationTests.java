package datsteam.currency_converter;

import datsteam.currency_converter.domain.currency.controller.CurrencyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyConverterApplicationTests {

	@Autowired
	private CurrencyController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
