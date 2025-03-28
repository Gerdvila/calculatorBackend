package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.request.MonthlyPaymentCalculationRequest;
import com.leasing.calculator.api.model.response.MonthlyPaymentCalculationResponse;
import com.leasing.calculator.service.MonthlyPaymentCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class CalculatorController {

    private final MonthlyPaymentCalculatorService monthlyPaymentCalculatorService;

    @Autowired
    public CalculatorController(MonthlyPaymentCalculatorService calculatorService) {
        this.monthlyPaymentCalculatorService = calculatorService;
    }

    @PostMapping("/calculator")
    @ResponseStatus(HttpStatus.OK)
    public MonthlyPaymentCalculationResponse calculateMonthlyPayment(@RequestBody MonthlyPaymentCalculationRequest monthlyPaymentCalculationRequest) {
        return monthlyPaymentCalculatorService.calculateMonthlyPayment(monthlyPaymentCalculationRequest);
    }

}
