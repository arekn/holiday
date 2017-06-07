package arekn.holiday.controller.handler;

import arekn.holiday.api.service.HolidayApiException;
import arekn.holiday.api.service.HolidayApiGatewayException;
import arekn.holiday.controller.request.HolidayRequestValidatorException;
import arekn.holiday.exception.HolidayApplicationException;
import arekn.holiday.service.HolidayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {HolidayApplicationException.class})
    public ResponseEntity applicationException(HolidayApplicationException ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        MessageBody messageBody = new MessageBody(httpStatus, ex.getMessage());
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {HolidayNotFoundException.class})
    public ResponseEntity holidayNotFoundException(HolidayNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        MessageBody messageBody = new MessageBody(httpStatus, ex.getMessage());
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {HolidayApiGatewayException.class})
    public ResponseEntity holidayApiGatewayException(HolidayApiGatewayException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        MessageBody messageBody = new MessageBody(httpStatus, ex.getMessage());
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {HolidayApiException.class})
    public ResponseEntity holidayApiException(HolidayApiException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        MessageBody messageBody = new MessageBody(httpStatus, ex.getMessage());
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {HolidayRequestValidatorException.class})
    public ResponseEntity requestValidationExceptions(HolidayRequestValidatorException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        MessageBody messageBody = new MessageBody(httpStatus, ex.getMessage());
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity requestBindExceptions(BindException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        MessageBody messageBody = new MessageBody(httpStatus, "Bad request parameter. Hint: Date should be in \"YYYY-MM-DD\" format");
        return new ResponseEntity(messageBody, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity generalErrors(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity(httpStatus);
    }
}
