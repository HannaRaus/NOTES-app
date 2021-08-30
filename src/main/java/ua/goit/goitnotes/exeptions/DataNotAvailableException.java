package ua.goit.goitnotes.exeptions;

public class DataNotAvailableException extends RuntimeException {
    public DataNotAvailableException(String message) {
        super(message);
    }
}
