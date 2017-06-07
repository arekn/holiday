package arekn.holiday.api.service;

import arekn.holiday.api.service.request.HolidayApiRequest;
import arekn.holiday.api.service.response.HolidayApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
class AsyncService {

    private final HolidayApiGateway gateway;

    @Autowired
    public AsyncService(HolidayApiGateway gateway) {
        this.gateway = gateway;
    }

    @Async
    public CompletableFuture<HolidayApiResponse> getAsyncHolidays(HolidayApiRequest request) {
        return CompletableFuture.completedFuture(gateway.getApiHolidays(request));
    }

    public void resolveFutures(List<CompletableFuture<HolidayApiResponse>> futures) {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

    public HolidayApiResponse getFromFuture(CompletableFuture<HolidayApiResponse> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new HolidayApiException("Resolving completable future exception", ex);
        }
    }
}
