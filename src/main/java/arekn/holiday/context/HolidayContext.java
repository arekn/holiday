package arekn.holiday.context;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class HolidayContext {

    private final LocalDate date;
    private final List<String> countries;

    public HolidayContext(LocalDate date, List<String> countries) {
        this.date = date;
        this.countries = countries;
    }

    public LocalDate getLaterDate(int delayInMonths) {
        return this.date.plusMonths(delayInMonths);
    }
}
