package com.leasing.calculator.repository;

import com.leasing.calculator.domain.aggregates.request.NoteRequestDO;
import com.leasing.calculator.domain.aggregates.response.NoteResponseDO;

import java.util.List;

public interface NoteRepositoryDAO {
    List<NoteResponseDO> selectNotesById(String applicationId);

    String createNote(NoteRequestDO note);
}
