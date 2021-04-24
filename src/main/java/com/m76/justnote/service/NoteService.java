package com.m76.justnote.service;

import com.m76.justnote.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotes();
    Note findNote(Long id);
    void addNote(Note note);
    void updateNote(Note note);
    void deleteNote(Note note);
}
