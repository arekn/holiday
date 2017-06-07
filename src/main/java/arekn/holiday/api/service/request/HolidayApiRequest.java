package arekn.holiday.api.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class HolidayApiRequest {
    private final String country;
    private final LocalDate baseDate;
    private final LocalDate searchDate;
}
