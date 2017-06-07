package arekn.holiday.controller.request;

import arekn.holiday.exception.HolidayApplicationException;

public class HolidayRequestValidatorException extends HolidayApplicationException {
    public HolidayRequestValidatorException(String message) {
        super(message);
    }

    public HolidayRequestValidatorException(Throwable cause) {
        super(cause);
    }
}
