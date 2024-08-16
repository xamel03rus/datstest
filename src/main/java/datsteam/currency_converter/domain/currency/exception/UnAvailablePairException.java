package datsteam.currency_converter.domain.currency.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UnAvailablePairException extends ResponseStatusException {
    public UnAvailablePairException() {
        super(HttpStatusCode.valueOf(404), "Unavailable pair");
    }
}
