package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.response.MailAndNotesResponse;
import com.leasing.calculator.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/note-mail-list/{id}")
    @Operation(summary = "Retrieve a list of notes and mails sorted by " +
                         "creation date and time in descending order, filtered by application ID.")
    @ResponseStatus(HttpStatus.OK)
    public MailAndNotesResponse getMailAndNoteById(@PathVariable String id) {
        return historyService.getNoteAndMailById(id);
    }
}