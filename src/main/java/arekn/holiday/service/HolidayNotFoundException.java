package arekn.holiday.service;

import arekn.holiday.exception.HolidayApplicationException;

public class HolidayNotFoundException extends HolidayApplicationException {
    public HolidayNotFoundException(String message) {
        super(message);
    }
}
