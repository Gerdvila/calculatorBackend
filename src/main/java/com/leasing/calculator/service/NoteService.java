package com.leasing.calculator.service;

import com.leasing.calculator.api.model.request.aggregates.NoteRequest;
import com.leasing.calculator.api.model.response.NoteResponse;
import com.leasing.calculator.domain.aggregates.request.NoteRequestDO;
import com.leasing.calculator.domain.aggregates.response.NoteResponseDO;
import com.leasing.calculator.repository.NoteRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepositoryDAO noteRepository;

    public NoteService(NoteRepositoryDAO noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteResponse> getNotesById(String id) {

        return noteRepository.selectNotesById(id).stream()
                .map(this::convertDaoResponseIntoNoteResponse)
                .toList();
    }

    public void createNote(NoteRequest noteRequest) {

        if (null == noteRequest|| null == noteRequest.noteText() || noteRequest.noteText().isEmpty()) {
            throw new IllegalArgumentException("Note request must not be null");
        }
        String noteText = noteRequest.noteText();
        String id = UUID.randomUUID().toString();
        NoteRequestDO daoRequest = new NoteRequestDO(id, noteRequest.applicationId(), noteText);

        noteRepository.createNote(daoRequest);
    }

    private NoteResponse convertDaoResponseIntoNoteResponse(NoteResponseDO note) {
        return note == null
                ? null
                : new NoteResponse(note.noteText());
    }
}
