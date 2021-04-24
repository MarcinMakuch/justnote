package com.m76.justnote.service;

import com.m76.justnote.entity.Note;
import com.m76.justnote.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class JpaNoteService implements NoteService {

    private final NoteRepository noteRepository;


    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note findNote(Long id) {
        return noteRepository.getOne(id);
    }

    @Override
    public void addNote(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void updateNote(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }
}
