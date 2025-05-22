package com.leasing.calculator.api.model.response;

import java.time.LocalDateTime;

public record NotesTextResponse(
        String notesText,
        LocalDateTime createdAt
) {
}
