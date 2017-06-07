package arekn.holiday.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HolidayServiceSettings {
    @Value("${holiday.service.searchRangeInMonths}")
    private Integer maxSearchRange;
}
