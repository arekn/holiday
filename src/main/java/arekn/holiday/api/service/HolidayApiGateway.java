package arekn.holiday.api.service;

import arekn.holiday.api.HolidayApiSettings;
import arekn.holiday.api.service.request.HolidayApiRequest;
import arekn.holiday.api.service.response.Holiday;
import arekn.holiday.api.service.response.HolidayApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
class HolidayApiGateway {

    private final RestTemplate restTemplate;

    private final HolidayApiSettings apiSettings;

    @Autowired
    public HolidayApiGateway(HolidayApiSettings apiSettings) {
        this.apiSettings = apiSettings;
        this.restTemplate = new RestTemplate();
    }

    public HolidayApiResponse getApiHolidays(HolidayApiRequest request) {
        try {
            UriComponentsBuilder ucb = buildBaseURI(request);
            URI uri = ucb.build().encode().toUri();
            HolidayApiResponse response = restTemplate.getForObject(uri, HolidayApiResponse.class);
            filter(request, response);
            return response;
        } catch (RestClientException ex) {
            throw new HolidayApiGatewayException("HolidayApi error: ex.getMessage()", ex);
        }
    }

    private void filter(HolidayApiRequest request, HolidayApiResponse response) {
        LocalDate baseDate = request.getBaseDate();
        List<Holiday> filteredHolidays = response.getHolidays()
                .stream().filter(ah -> baseDate.isBefore(ah.getDate()))
                .collect(Collectors.toList());
        response.setHolidays(filteredHolidays);
    }

    private UriComponentsBuilder buildBaseURI(HolidayApiRequest request) {
        return UriComponentsBuilder.fromHttpUrl(apiSettings.getEndpoint())
                .queryParam("key", apiSettings.getKey())
                .queryParam("country", request.getCountry())
                .queryParam("year", request.getSearchDate().getYear())
                .queryParam("month", request.getSearchDate().getMonthValue());
    }
}
