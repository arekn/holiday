package arekn.holiday.controller.request;

import org.springframework.stereotype.Component;

@Component
public class HolidayRequestValidator {
    private static final int MIN_COUNTRY_COUNT = 2;

    public void validate(HolidayRequest request) {
        if (request.getCountries() == null) {
            throw new HolidayRequestValidatorException("Country list is null.");
        }
        for (String country : request.getCountries()) {
            if (country == null || country.isEmpty()) {
                throw new HolidayRequestValidatorException("Country is null.");
            }
        }
        if (request.getCountries().size() < MIN_COUNTRY_COUNT) {
            throw new HolidayRequestValidatorException("Country list is too short.");
        }
        if (request.getDate() == null) {
            throw new HolidayRequestValidatorException("Date is null.");
        }
    }
}
