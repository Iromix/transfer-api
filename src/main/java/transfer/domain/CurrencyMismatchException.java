package transfer.domain;

public class CurrencyMismatchException extends RuntimeException {
    CurrencyMismatchException(String message) {
        super(message);
    }
}
