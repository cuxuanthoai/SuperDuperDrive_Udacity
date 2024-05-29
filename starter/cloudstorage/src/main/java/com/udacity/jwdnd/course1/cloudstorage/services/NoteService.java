package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteMapper notes;

    public NoteService(NoteMapper mapper) {
        this.notes = mapper;
    }

    public List<Note> fetchAllByUserId(String UID) {
        return notes.fetchAllByUserId(UID);
    }

    public void remove(Note note) {
        notes.remove(note);
    }

    public void save(Note note) {
        if (note.getId() == null) {
            notes.save(note);
            return;
        }

        notes.modify(note);
    }

}