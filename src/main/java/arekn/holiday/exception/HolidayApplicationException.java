package arekn.holiday.exception;

public class HolidayApplicationException extends RuntimeException {

    public HolidayApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HolidayApplicationException(String message) {
        super(message);
    }

    public HolidayApplicationException(Throwable cause) {
        super(cause);
    }
}