package com.leasing.calculator.api.model.request.aggregates;

public record NoteRequest(
        String applicationId,
        String noteText
) {
}
