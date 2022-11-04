package ua.nure.danielost.shuttler.exception;

public class InvalidVinCodeException extends Exception {
    public InvalidVinCodeException() {
    }

    public InvalidVinCodeException(String message) {
        super(message);
    }
}
