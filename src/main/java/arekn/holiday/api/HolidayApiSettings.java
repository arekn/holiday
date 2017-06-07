package arekn.holiday.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HolidayApiSettings {

    @Value("${holiday.api.endpoint}")
    private String endpoint;

    @Value("${holiday.api.key}")
    private String key;
}
