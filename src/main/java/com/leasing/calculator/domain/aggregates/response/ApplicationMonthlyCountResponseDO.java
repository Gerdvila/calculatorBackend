package com.leasing.calculator.domain.aggregates.response;

public record ApplicationMonthlyCountResponseDO(
        int thisMonthCount,
        int previousMonthCount
){
}