package arekn.holiday.api.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayApiResponse {
    private Integer status;
    private List<Holiday> holidays;
}
