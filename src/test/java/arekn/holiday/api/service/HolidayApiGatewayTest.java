package arekn.holiday.api.service;

import arekn.holiday.api.HolidayApiSettings;
import arekn.holiday.api.service.request.HolidayApiRequest;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.api.service.response.HolidayApiResponse;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

public class HolidayApiGatewayTest {

    @Mock
    private HolidayApiSettings settings;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HolidayApiGateway sut;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getApiHolidaysShouldReturnFilteredResponse() throws Exception {
        // given
        Mockito.when(settings.getEndpoint()).thenReturn("https://testservice.test/holidays");
        Mockito.when(settings.getKey()).thenReturn("testApiKey");

        LocalDate requestDate = LocalDate.parse("2000-01-01");
        LocalDate afterFilterDate = LocalDate.parse("2000-01-20");
        LocalDate filterDate = LocalDate.parse("2000-01-15");

        Holiday filteredHoliday = new Holiday("Holiday1", requestDate);
        Holiday validHoliday = new Holiday("Holiday2", afterFilterDate);
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(filteredHoliday);
        holidays.add(validHoliday);
        HolidayApiResponse response = new HolidayApiResponse();
        response.setHolidays(holidays);
        Mockito.when(restTemplate.getForObject(any(URI.class), any())).thenReturn(response);
        HolidayApiRequest request = new HolidayApiRequest("TEST", requestDate, filterDate);

        // when
        HolidayApiResponse apiHolidays = sut.getApiHolidays(request);

        // then
        List<Holiday> holidayList = apiHolidays.getHolidays();
        Assertions.assertThat(holidayList).hasSize(1).containsExactly(validHoliday);
    }
}