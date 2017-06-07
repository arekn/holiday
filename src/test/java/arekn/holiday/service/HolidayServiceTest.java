package arekn.holiday.service;


import arekn.holiday.api.service.HolidayApiService;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.context.HolidayContext;
import arekn.holiday.controller.response.CommonHolidays;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;

public class HolidayServiceTest {

    @Mock
    private HolidayApiService holidayApi;

    @Mock
    private HolidayServiceSettings serviceSettings;

    @InjectMocks
    private HolidayService sut;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findCommonHolidaysShouldFindCommonHolidays() throws Exception {
        // given
        LocalDate requestDate = LocalDate.parse("2000-01-01");
        LocalDate holidayDate1 = LocalDate.parse("2000-01-20");
        LocalDate holidayDate4 = LocalDate.parse("2000-01-21");
        LocalDate commonDate = LocalDate.parse("2000-01-15");

        List<String> countries = Arrays.asList(new String[]{"PL", "US"});
        HolidayContext context = new HolidayContext(requestDate, countries);

        String commonHoliday1 = "CommonHoliday1";
        String commonHoliday2 = "CommonHoliday2";

        Holiday holiday1 = new Holiday("Holiday1", holidayDate1);
        Holiday holiday2 = new Holiday(commonHoliday1, commonDate);
        Holiday holiday3 = new Holiday(commonHoliday2, commonDate);
        Holiday holiday4 = new Holiday("Holiday4", holidayDate4);
        List<Holiday> holidays11 = new ArrayList<>();
        List<Holiday> holidays12 = new ArrayList<>();
        List<Holiday> holidays21 = new ArrayList<>();
        holidays21.add(holiday1);
        holidays21.add(holiday2);
        List<Holiday> holidays22 = new ArrayList<>();
        holidays22.add(holiday3);
        holidays22.add(holiday4);

        List<List<Holiday>> apiHolidays1 = new ArrayList<>();
        apiHolidays1.add(holidays11);
        apiHolidays1.add(holidays12);
        List<List<Holiday>> apiHolidays2 = new ArrayList<>();
        apiHolidays2.add(holidays21);
        apiHolidays2.add(holidays22);

        Mockito.when(holidayApi.getApiHolidays(any(), any())).thenReturn(apiHolidays1).thenReturn(apiHolidays2);
        Mockito.when(serviceSettings.getMaxSearchRange()).thenReturn(10);

        // when
        CommonHolidays commonHolidays = sut.findCommonHolidays(context);

        // then
        Assertions.assertThat(commonHolidays.getDate()).isEqualTo(commonDate.toString());
        Assertions.assertThat(commonHolidays.getNames()).containsExactlyInAnyOrder(commonHoliday1, commonHoliday2);
    }
}