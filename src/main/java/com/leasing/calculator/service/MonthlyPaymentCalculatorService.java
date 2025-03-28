package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.MonthlyPaymentCalculationRequest;
import com.leasing.calculator.api.model.response.MonthlyPaymentCalculationResponse;
import com.leasing.calculator.domain.aggregates.response.InterestRateResponseDO;
import com.leasing.calculator.repository.InterestRateRepositoryDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class MonthlyPaymentCalculatorService {

    private final InterestRateRepositoryDAO interestRateRepositoryDAO;

    public MonthlyPaymentCalculatorService(InterestRateRepositoryDAO interestRateRepositoryDAO) {
        this.interestRateRepositoryDAO = interestRateRepositoryDAO;
    }

    public MonthlyPaymentCalculationResponse calculateMonthlyPayment(MonthlyPaymentCalculationRequest monthlyPaymentCalculationRequest) {
        InterestRateResponseDO interestRateResponseDO = interestRateRepositoryDAO.fetchAllInterestRate();

        BigDecimal interestRate = getInterestRate(monthlyPaymentCalculationRequest, interestRateResponseDO);
        BigDecimal residualValue = calculateResidualValue(monthlyPaymentCalculationRequest);
        BigDecimal loanAmountMinusDownPayment = calculateLoanAmountMinusDownPayment(monthlyPaymentCalculationRequest);
        BigDecimal monthlyInterest = calculateMonthlyInterest(loanAmountMinusDownPayment, interestRate, monthlyPaymentCalculationRequest.period());
        BigDecimal monthlyLoanAmount = calculateMonthlyLoanAmount(loanAmountMinusDownPayment, residualValue, monthlyPaymentCalculationRequest.period());

        BigDecimal monthlyPayment = monthlyLoanAmount.add(monthlyInterest);
        return new MonthlyPaymentCalculationResponse(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
    }

    private BigDecimal getInterestRate(MonthlyPaymentCalculationRequest request, InterestRateResponseDO interestRateResponseDO) {
        BigDecimal interestRate = request.isEcoFriendly() ? interestRateResponseDO.eco() : interestRateResponseDO.regular();
        return interestRate.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
    }

    private BigDecimal calculateResidualValue(MonthlyPaymentCalculationRequest request) {
        return request.carValue()
                .multiply(BigDecimal.valueOf(request.residualValuePercentage()))
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
    }

    private BigDecimal calculateLoanAmountMinusDownPayment(MonthlyPaymentCalculationRequest request) {
        return request.carValue().subtract(request.downPayment());
    }

    private BigDecimal calculateMonthlyInterest(BigDecimal loanAmount, BigDecimal rate, int loanMonthPeriod) {
        BigDecimal totalInterest = loanAmount.multiply(rate)
                .multiply(BigDecimal.valueOf(loanMonthPeriod))
                .divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        return totalInterest.divide(BigDecimal.valueOf(loanMonthPeriod), MathContext.DECIMAL128);
    }

    private BigDecimal calculateMonthlyLoanAmount(BigDecimal loanAmount, BigDecimal residualValue, int loanMonthPeriod) {
        return loanAmount.subtract(residualValue)
                .divide(BigDecimal.valueOf(loanMonthPeriod), MathContext.DECIMAL128);
    }
}