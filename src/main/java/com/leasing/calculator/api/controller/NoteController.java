package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.request.aggregates.NoteRequest;
import com.leasing.calculator.api.model.response.NoteResponse;
import com.leasing.calculator.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNote(@RequestBody NoteRequest noteRequest) {
        noteService.createNote(noteRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notes by application id")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> getNotesById(@PathVariable String id) {
        return noteService.getNotesById(id);
    }
}

