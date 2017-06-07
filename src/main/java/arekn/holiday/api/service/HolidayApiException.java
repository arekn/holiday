package arekn.holiday.api.service;

import arekn.holiday.exception.HolidayApplicationException;

public class HolidayApiException extends HolidayApplicationException {
    public HolidayApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public HolidayApiException(String message) {
        super(message);
    }
}
