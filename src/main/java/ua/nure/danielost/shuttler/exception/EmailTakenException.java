package ua.nure.danielost.shuttler.exception;

public class EmailTakenException extends Exception {
    public EmailTakenException(String message) {
        super(message);
    }
}
