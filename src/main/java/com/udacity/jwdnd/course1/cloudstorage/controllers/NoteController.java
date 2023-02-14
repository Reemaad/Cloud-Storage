package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/addNote")
    public String addNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        String noteSuccess = null;
        String noteError = null;

        if (noteService.isNoteAlreadyExist(note.getNoteId())) {
            noteService.updateNote(note);
            noteSuccess = "Note updated successfully!";
        } else {
            int rowNumber = noteService.addNote(note, authentication.getName());
            if (rowNumber < 0) {
                noteError = "Something went wrong while adding your note. Please try again.";
            } else {
                noteSuccess = "Note added successfully!";
            }
        }

        if (noteError == null) {
            model.addAttribute("noteSuccess", noteSuccess);
        } else {
            model.addAttribute("noteError", noteError);
        }

        List<Note> userNotes = noteService.getUserNotes(authentication.getName());
        model.addAttribute("notes", userNotes);
        return "home";
    }

    @GetMapping("/deleteNote")
    public String deleteFile(@RequestParam("noteId") Integer noteId, Authentication authentication, Model model) {
        String noteSuccess = null;
        String noteError = null;

        boolean isFileDeletedSuccessfully = noteService.deleteNote(noteId);

        if (isFileDeletedSuccessfully) {
            noteSuccess = "Note deleted successfully!";
        } else {
            noteError = "Something went wrong while deleting Your note. Please try again.";
        }

        if (noteError == null) {
            model.addAttribute("noteSuccess", noteSuccess);
        } else {
            model.addAttribute("noteError", noteError);
        }

        List<Note> userNotes = noteService.getUserNotes(authentication.getName());
        model.addAttribute("notes", userNotes);

        return "home";
    }
}
