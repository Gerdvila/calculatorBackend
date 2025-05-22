package com.leasing.calculator.service;

import com.leasing.calculator.api.model.response.MailAndNotesResponse;
import com.leasing.calculator.api.model.response.MailTextResponse;
import com.leasing.calculator.api.model.response.NotesTextResponse;
import com.leasing.calculator.domain.aggregates.response.MailResponseDO;
import com.leasing.calculator.domain.aggregates.response.NoteResponseDO;
import com.leasing.calculator.repository.MailRepositoryDAO;
import com.leasing.calculator.repository.NoteRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

private final NoteRepositoryDAO noteRepository;
private final MailRepositoryDAO mailRepository;
    public HistoryService(NoteRepositoryDAO noteRepository, MailRepositoryDAO mailRepository) {
        this.noteRepository = noteRepository;
        this.mailRepository = mailRepository;
    }

public MailAndNotesResponse getNoteAndMailById(String id) {
    List<MailResponseDO> responseMails = mailRepository.selectMailByApplicationId(id);
    List<NoteResponseDO> responseNotes = noteRepository.selectNotesById(id);
    List<NotesTextResponse> combinedNotesResponses = new ArrayList<>();
    List<MailTextResponse> combinedMailsResponses = new ArrayList<>();
    if(responseMails.isEmpty() && responseNotes.isEmpty()) {
        return new MailAndNotesResponse(id, combinedNotesResponses, combinedMailsResponses);
    }
    if(!responseMails.isEmpty()) {
        for (MailResponseDO mail : responseMails) {
            combinedMailsResponses.add(convertDAOResponseIntoMailResponseList(mail));
        }
    }

    if(!responseNotes.isEmpty()) {
        for (NoteResponseDO note : responseNotes) {
            combinedNotesResponses.add(convertDAOResponseIntoNoteResponseList(note));
        }
    }

    return new MailAndNotesResponse(id, combinedNotesResponses, combinedMailsResponses);
}

private NotesTextResponse convertDAOResponseIntoNoteResponseList(NoteResponseDO noteDAOResponse) {
    return new NotesTextResponse(
            noteDAOResponse.noteText(),
            noteDAOResponse.createdAt()
    );
}

private MailTextResponse convertDAOResponseIntoMailResponseList(MailResponseDO mailDAOResponse) {
    return new MailTextResponse(
            mailDAOResponse.mailText(),
            mailDAOResponse.createdAt()
    );
}
}