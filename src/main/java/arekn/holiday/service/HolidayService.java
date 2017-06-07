package arekn.holiday.service;

import arekn.holiday.context.HolidayContext;
import arekn.holiday.api.service.HolidayApiService;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.controller.response.CommonHolidays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HolidayService {

    private final int firstElementIndex = 0;

    private final HolidayApiService holidayApi;

    @Value("${holiday.service.searchRangeInMonths}")
    private Integer maxSearchRange;

    @Autowired
    public HolidayService(HolidayApiService holidayApi) {
        this.holidayApi = holidayApi;
    }

    public CommonHolidays findCommonHolidays(HolidayContext context) {
        int dateDelayInMonths = 0;
        do {
            LocalDate searchPeriod = context.getLaterDate(dateDelayInMonths);
            List<List<Holiday>> apiHolidays = holidayApi.getApiHolidays(context, searchPeriod);
            List<Holiday> foundCommonHolidays = findCommonElements(apiHolidays);
            if (!foundCommonHolidays.isEmpty()) {
                return common(foundCommonHolidays, apiHolidays);
            } else {
                dateDelayInMonths++;
            }
        } while (dateDelayInMonths < maxSearchRange);
        throw new HolidayNotFoundException("Common holiday not found");
    }

    private CommonHolidays common(List<Holiday> commonHolidays, List<List<Holiday>> apiHolidays) {
        Holiday earliestCommonHoliday = getEarliestHoliday(commonHolidays);
        List<String> holidayNames = new ArrayList<>();
        for (List<Holiday> dd : apiHolidays) {
            holidayNames.add(dd.get(dd.indexOf(earliestCommonHoliday)).getName());
        }
        return new CommonHolidays(earliestCommonHoliday.getDate().toString(), holidayNames);
    }

    private Holiday getEarliestHoliday(List<Holiday> holidays) {
        Collections.sort(holidays);
        return holidays.get(firstElementIndex);
    }

    private List<Holiday> findCommonElements(List<List<Holiday>> allHolidays) {
        List<Holiday> retain = allHolidays.get(firstElementIndex);
        for (int index = 1; index < allHolidays.size(); index++) {
            List<Holiday> holidayList = allHolidays.get(index);
            retain.retainAll(holidayList);
        }
        return retain;
    }
}