package arekn.holiday.context;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Collections;

public class HolidayContextTest {

    private HolidayContext sut;

    @Test
    public void getLaterDateShouldReturnValidLaterDate() throws Exception {
        // given
        sut = new HolidayContext(LocalDate.parse("2000-01-01"), Collections.emptyList());

        // when
        LocalDate laterDate = sut.getLaterDate(3);

        // then
        Assertions.assertThat(laterDate.toString()).isEqualTo("2000-04-01");
    }
}