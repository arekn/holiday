package arekn.holiday.api.service;

import arekn.holiday.exception.HolidayApplicationException;

public class HolidayApiGatewayException extends HolidayApplicationException {
    public HolidayApiGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
