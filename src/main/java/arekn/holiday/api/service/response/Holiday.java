package arekn.holiday.api.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(exclude = "name")
@NoArgsConstructor
@AllArgsConstructor
public class Holiday implements Comparable<Holiday> {
    private String name;
    private LocalDate date;

    @Override
    public int compareTo(Holiday holiday) {
        return this.date.compareTo(holiday.getDate());
    }
}