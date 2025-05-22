package com.leasing.calculator.api.model.request.aggregates;

public record MailRequest (
        String applicationId,
        String mailSubject,
        String mailText,
        String mailRecipient
){
}
