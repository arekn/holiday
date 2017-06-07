package arekn.holiday;

import arekn.holiday.controller.response.CommonHolidays;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HolidayApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void validParametersShouldReturnExpectedResults() throws Exception {
        // given
        String twoCountries = "PL,US";
        URI testURI = getTestURI(twoCountries);

        // when
        CommonHolidays commonHolidays = restTemplate.getForObject(testURI, CommonHolidays.class);

        // then
        Assertions.assertThat(commonHolidays.getDate()).isNotBlank();
        Assertions.assertThat(commonHolidays.getNames()).hasSize(2).doesNotContainNull().doesNotHaveDuplicates();
    }

    @Test
    public void extendedParametersShouldReturnExpectedResults() throws Exception {
        // given
        String threeCountries = "PL,US,DE";
        URI testURI = getTestURI(threeCountries);

        // when
        CommonHolidays commonHolidays = restTemplate.getForObject(testURI, CommonHolidays.class);

        // then
        Assertions.assertThat(commonHolidays.getDate()).isNotBlank();
        Assertions.assertThat(commonHolidays.getNames()).hasSize(3).doesNotContainNull().doesNotHaveDuplicates();
    }

    private URI getTestURI(String countries) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/holidays")
                .queryParam("date", "2000-01-01")
                .queryParam("countries", countries);
        return builder.build().encode().toUri();
    }
}
