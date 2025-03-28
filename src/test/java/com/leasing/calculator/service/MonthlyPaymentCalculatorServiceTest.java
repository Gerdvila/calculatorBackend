package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.MonthlyPaymentCalculationRequest;
import com.leasing.calculator.api.model.response.MonthlyPaymentCalculationResponse;
import com.leasing.calculator.domain.aggregates.response.InterestRateResponseDO;
import com.leasing.calculator.repository.InterestRateRepositoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MonthlyPaymentCalculatorServiceTest {

    @Mock
    private InterestRateRepositoryDAO interestRateRepositoryDAO;

    @InjectMocks
    private MonthlyPaymentCalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateMonthlyPaymentWithEcoFriendlyRate() {
        MonthlyPaymentCalculationRequest request = new MonthlyPaymentCalculationRequest(
                BigDecimal.valueOf(20000),
                5,
                BigDecimal.valueOf(5000),
                36,
                true
        );

        InterestRateResponseDO mockInterestRates = new InterestRateResponseDO(BigDecimal.valueOf(3.5),
                BigDecimal.valueOf(5.0)
        );

        when(interestRateRepositoryDAO.fetchAllInterestRate()).thenReturn(mockInterestRates);

        MonthlyPaymentCalculationResponse response = calculatorService.calculateMonthlyPayment(request);

        assertEquals(BigDecimal.valueOf(1622.50).setScale(2), response.monthlyPayment());
    }

    @Test
    void testCalculateMonthlyPaymentWithRegularRate() {
        MonthlyPaymentCalculationRequest request = new MonthlyPaymentCalculationRequest(
                BigDecimal.valueOf(20000),
                5000,
                BigDecimal.valueOf(5),
                36,
                false
        );

        InterestRateResponseDO mockInterestRates = new InterestRateResponseDO(BigDecimal.valueOf(3.5),
                BigDecimal.valueOf(5.0)
                );
        when(interestRateRepositoryDAO.fetchAllInterestRate()).thenReturn(mockInterestRates);

        MonthlyPaymentCalculationResponse response = calculatorService.calculateMonthlyPayment(request);

        assertEquals(BigDecimal.valueOf(60.88).setScale(2), response.monthlyPayment());
    }
}