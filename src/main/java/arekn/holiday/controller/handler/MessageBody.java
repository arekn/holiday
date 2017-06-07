package arekn.holiday.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
class MessageBody {
    private HttpStatus status;
    private String message;
}
