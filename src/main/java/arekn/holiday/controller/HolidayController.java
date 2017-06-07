package arekn.holiday.controller;

import arekn.holiday.context.HolidayContext;
import arekn.holiday.controller.request.HolidayRequest;
import arekn.holiday.controller.request.HolidayRequestValidator;
import arekn.holiday.controller.response.CommonHolidays;
import arekn.holiday.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HolidayController {

    private final HolidayService holidayService;

    private final HolidayRequestValidator validator;

    @Autowired
    public HolidayController(HolidayService holidayService, HolidayRequestValidator validator) {
        this.holidayService = holidayService;
        this.validator = validator;
    }

    @GetMapping("/holidays")
    public CommonHolidays getCommonHoliday(HolidayRequest request) {
        HolidayContext context = getContextFromValidRequest(request);
        return holidayService.findCommonHolidays(context);

    }

    private HolidayContext getContextFromValidRequest(HolidayRequest request) {
        validator.validate(request);
        return new HolidayContext(request.getDate(), request.getCountries());
    }

}