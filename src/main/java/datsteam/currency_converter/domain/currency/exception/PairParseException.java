package datsteam.currency_converter.domain.currency.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PairParseException extends ResponseStatusException {
    public PairParseException() {
        super(HttpStatusCode.valueOf(503), "Couldn't get result");
    }
}
