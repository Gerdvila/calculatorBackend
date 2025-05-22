package com.leasing.calculator.domain.aggregates.request;

public record NoteRequestDO(
        String id,
        String applicationId,
        String noteText
) {
}
