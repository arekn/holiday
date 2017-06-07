package arekn.holiday.api.service;

import arekn.holiday.context.HolidayContext;
import arekn.holiday.api.service.request.HolidayApiRequest;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.api.service.response.HolidayApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class HolidayApiService {

    private final AsyncService asyncService;

    @Autowired
    public HolidayApiService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public List<List<Holiday>> getApiHolidays(HolidayContext context, LocalDate searchDate) {
        List<HolidayApiRequest> requests = getHolidayApiRequestList(context, searchDate);
        List<List<Holiday>> holidays = getHolidaysAsynchronously(requests);
        verifyApiResponse(requests, holidays);
        return holidays;
    }

    private void verifyApiResponse(List<HolidayApiRequest> requests, List<List<Holiday>> holidays) {
        if (requests.size() != holidays.size()) {
            throw new HolidayApiException("Returned holiday size: " + requests.size() +
                    "is different than requested: " + requests.size());
        }
    }

    private List<List<Holiday>> getHolidaysAsynchronously(List<HolidayApiRequest> requests) {
        List<CompletableFuture<HolidayApiResponse>> futureHolidays =
                requests.stream().map(asyncService::getAsyncHolidays).collect(Collectors.toList());
        asyncService.resolveFutures(futureHolidays);
        return futureHolidays.stream()
                .map(asyncService::getFromFuture)
                .map(HolidayApiResponse::getHolidays)
                .collect(Collectors.toList());
    }

    private List<HolidayApiRequest> getHolidayApiRequestList(HolidayContext context, LocalDate searchDate) {
        return context.getCountries()
                .stream()
                .map(country -> getHolidayApiRequest(country, context.getDate(), searchDate))
                .collect(Collectors.toList());
    }

    private HolidayApiRequest getHolidayApiRequest(String country, LocalDate date, LocalDate delayedDate) {
        return new HolidayApiRequest(country, date, delayedDate);
    }
}
