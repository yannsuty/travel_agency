package fr.lernejo.travelsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CannotReadCountryFile extends RuntimeException {
    public CannotReadCountryFile(String message) {
        super(message);
    }
}
