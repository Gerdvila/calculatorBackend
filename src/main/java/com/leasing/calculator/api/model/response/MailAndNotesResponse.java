package com.leasing.calculator.api.model.response;

import java.util.List;

public record MailAndNotesResponse
        (
                String applicationId,
                List<NotesTextResponse> notesTexts,
                List<MailTextResponse> mailTexts
        ) {
}