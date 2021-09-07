package ua.goit.goitnotes.error_handling;

public class DataNotAvailableException extends RuntimeException {
    public DataNotAvailableException(String message) {
        super(message);
    }
}
