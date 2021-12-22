package fr.lernejo.travelsite.exception;

public class CannotReadCountryFile extends RuntimeException {
    public CannotReadCountryFile(String message) {
        super(message);
    }
}
