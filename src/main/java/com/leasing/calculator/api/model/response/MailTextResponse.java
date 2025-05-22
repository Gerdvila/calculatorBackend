package com.leasing.calculator.api.model.response;

import java.time.LocalDateTime;

public record MailTextResponse(
        String mailText,
        LocalDateTime createdAt
) {
}
