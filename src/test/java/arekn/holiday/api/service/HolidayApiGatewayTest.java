package arekn.holiday.api.service;

import arekn.holiday.api.HolidayApiSettings;
import arekn.holiday.api.service.request.HolidayApiRequest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HolidayApiGatewayTest {

    @Mock
    private HolidayApiSettings settings;


    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testName() throws Exception {
        Mockito.when(settings.getEndpoint()).thenReturn("https://holidayapi.com/v1/holidays");
        Mockito.when(settings.getKey()).thenReturn("1d3526af-ee45-4241-b4db-5100f55e77d3");

        HolidayApiGateway sut = new HolidayApiGateway(settings);

        //HolidayApiRequest request = new HolidayApiRequest(2000, 06, "PL");
        //HolidayApiResponse pl = sut.getHolidays(request);


    }
}