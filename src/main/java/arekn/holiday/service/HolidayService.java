package arekn.holiday.service;

import arekn.holiday.context.HolidayContext;
import arekn.holiday.api.service.HolidayApiService;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.controller.response.CommonHolidays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HolidayService {

    private final int firstElementIndex = 0;

    private final HolidayApiService holidayApi;

    private final HolidayServiceSettings serviceSettings;

    @Autowired
    public HolidayService(HolidayApiService holidayApi, HolidayServiceSettings serviceSettings) {
        this.holidayApi = holidayApi;
        this.serviceSettings = serviceSettings;
    }

    public CommonHolidays findCommonHolidays(HolidayContext context) {
        int dateDelayInMonths = 0;
        do {
            LocalDate searchPeriod = context.getLaterDate(dateDelayInMonths);
            List<List<Holiday>> apiHolidays = holidayApi.getApiHolidays(context, searchPeriod);
            List<Holiday> commonHolidays = retainCommonHolidays(apiHolidays);
            if (!commonHolidays.isEmpty()) {
                return buildResponse(commonHolidays, apiHolidays);
            } else {
                dateDelayInMonths++;
            }
        } while (dateDelayInMonths < serviceSettings.getMaxSearchRange());
        throw new HolidayNotFoundException("Common holiday not found");
    }

    private List<Holiday> retainCommonHolidays(List<List<Holiday>> allHolidays) {
        List<Holiday> retain = allHolidays.get(firstElementIndex);
        for (int index = 1; index < allHolidays.size(); index++) {
            List<Holiday> holidayList = allHolidays.get(index);
            retain.retainAll(holidayList);
        }
        return retain;
    }

    private CommonHolidays buildResponse(List<Holiday> commonHolidays, List<List<Holiday>> apiHolidays) {
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
}