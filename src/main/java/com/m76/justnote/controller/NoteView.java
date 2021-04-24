package com.m76.justnote.controller;

import com.m76.justnote.entity.Note;
import com.m76.justnote.service.JpaNoteService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = NoteView.ROUTE)
@PageTitle("justNote")
public class NoteView extends VerticalLayout {

    public static final String ROUTE = "justnote";
    private JpaNoteService jpaNoteService;

    private FormLayout notesForm;
    private Long id;
    private TextField textFieldNoteTitle;
    private TextField textFieldNoteContent;
    private Button saveButtonNote;
    private Notification notificationError;

    private Grid<Note> grid;
    private List<Note> noteList;

    public NoteView(JpaNoteService jpaNoteService) {
        this.jpaNoteService = jpaNoteService;
        initNoteForm();
        initNoteTable();
    }

    private void initNoteForm() {
        notesForm = new FormLayout();
        notesForm.setId("note-form");

        textFieldNoteTitle = new TextField();
        textFieldNoteTitle.setTitle("Title");
        textFieldNoteTitle.setPlaceholder("Note Title");
        textFieldNoteTitle.setId("note-title");

        textFieldNoteContent = new TextField();
        textFieldNoteContent.setTitle("Note");
        textFieldNoteContent.setPlaceholder("Note Content");
        textFieldNoteContent.setId("note-content");
        textFieldNoteContent.setWidth(300, Unit.PIXELS);
        textFieldNoteContent.setHeight(300, Unit.PIXELS);

        saveButtonNote = new Button();
        saveButtonNote.setText("Save");
        saveButtonNote.addClickListener(buttonClickEvent -> {
            if (textFieldNoteTitle.isEmpty() || textFieldNoteContent.isEmpty()) {
                notificationError.setText("Please provide title and content of the Note");
                notificationError.open();
            } else {
                jpaNoteService.addNote(Note.builder()
                        .noteTitle(textFieldNoteTitle.getValue())
                        .noteContent(textFieldNoteContent.getValue())
                        .id(id)
                        .build());
                clearFormValues();
            }
        });
        saveButtonNote.setId("save-button-note");
        VerticalLayout vertical = new VerticalLayout ();
        vertical.add(textFieldNoteTitle);
        vertical.add(textFieldNoteContent);
        vertical.add(saveButtonNote);
        notesForm.add(vertical);

        notesForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3),
                new FormLayout.ResponsiveStep("40em", 4)
        );
        notificationError = new Notification();
        notificationError.setDuration(2000);
        notificationError.setPosition(Notification.Position.MIDDLE);
        notificationError.setId("notification-error");
        add(notesForm);

    }

    private void initNoteTable() {
        grid = new Grid<>(Note.class);
        grid.setId("note-grid");
        refreshNoteGrid();
        grid.removeColumnByKey("id");

        grid.addComponentColumn(item -> new Button(VaadinIcon.EDIT.create(), click -> {
            textFieldNoteTitle.setValue(item.getNoteTitle());
            textFieldNoteContent.setId(item.getNoteContent());
            id = item.getId();
            refreshNoteGrid();
        })).setWidth("5px");

        grid.addComponentColumn(item -> new Button(VaadinIcon.TRASH.create(), click -> {
            jpaNoteService.deleteNote(item);
            refreshNoteGrid();
        })).setWidth("15px");
        add(grid);
    }

    private void clearFormValues() {
        textFieldNoteTitle.clear();
        textFieldNoteContent.clear();
        id = null;
    }
    private void refreshNoteGrid() {
        noteList = jpaNoteService.getNotes();
        grid.setItems(noteList);
    }
}
