package com.leasing.calculator.domain.aggregates.request;

public record MailRequestDO(
        String id,
        String applicationId,
        String mailText
){
}