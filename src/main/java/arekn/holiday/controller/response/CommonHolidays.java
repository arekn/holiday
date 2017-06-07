package arekn.holiday.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommonHolidays {
    private final String date;
    private final List<String> names;
}