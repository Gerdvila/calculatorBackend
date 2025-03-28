package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.request.aggregates.MonthlyPaymentCalculationRequest;
import com.leasing.calculator.api.model.response.MonthlyPaymentCalculationResponse;
import com.leasing.calculator.service.MonthlyPaymentCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Tag(name = "Monthly Payment Calculator", description = "Provides functionality to calculate monthly payments for leasing services.")
public class CalculatorController {

    private final MonthlyPaymentCalculatorService monthlyPaymentCalculatorService;

    @Autowired
    public CalculatorController(MonthlyPaymentCalculatorService calculatorService) {
        this.monthlyPaymentCalculatorService = calculatorService;
    }

    @Operation(
            summary = "Calculate Monthly Payment",
            description = "Calculates the monthly payment for leasing based on provided details such as car value, down payment, interest rate, and loan duration."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monthly payment calculated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonthlyPaymentCalculationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/calculator")
    @ResponseStatus(HttpStatus.OK)
    public MonthlyPaymentCalculationResponse calculateMonthlyPayment(@RequestBody MonthlyPaymentCalculationRequest monthlyPaymentCalculationRequest) {
        return monthlyPaymentCalculatorService.calculateMonthlyPayment(monthlyPaymentCalculationRequest);
    }
}