package arekn.holiday.api.service;

import arekn.holiday.api.service.request.HolidayApiRequest;
import arekn.holiday.api.service.response.HolidayApiResponse;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncServiceTest {

    @Mock
    private HolidayApiGateway gateway;

    @Mock
    private HolidayApiRequest request;

    @Mock
    private HolidayApiResponse response;

    @Mock
    private CompletableFuture<HolidayApiResponse> future;

    @InjectMocks
    private AsyncService sut;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getFromFutureShouldReturnObject() throws Exception {
        // given
        Mockito.when(future.get()).thenReturn(response);

        // when
        HolidayApiResponse fromFuture = sut.getFromFuture(future);

        // then
        Assertions.assertThat(fromFuture).isEqualTo(response);
    }

    @Test(expectedExceptions = HolidayApiException.class)
    public void interruptedExceptionShouldBeThrownAsHolidayApiException() throws Exception {
        // given
        Mockito.when(future.get()).thenThrow(InterruptedException.class);

        // when
        sut.getFromFuture(future);

        // then
    }

    @Test(expectedExceptions = HolidayApiException.class)
    public void executionExceptionShouldBeThrownAsHolidayApiException() throws Exception {
        // given
        Mockito.when(future.get()).thenThrow(ExecutionException.class);

        // when
        sut.getFromFuture(future);

        // then
    }
}