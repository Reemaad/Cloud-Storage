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

    @PostMapping("/submitNote")
    public String submitNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        String result = null;

        if (noteService.isNoteAlreadyExist(note.getNoteId())) {
            noteService.updateNote(note);
            result = "success";
        } else {
            int rowNumber = noteService.addNote(note, authentication.getName());
            if (rowNumber < 0) {
                result = "error";
            } else {
                result = "success";
            }
        }

        return "redirect:result?"+result+"=true";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Authentication authentication, Model model) {
        String result = null;

        boolean isFileDeletedSuccessfully = noteService.deleteNote(noteId);

        if (isFileDeletedSuccessfully) {
            result = "success";
        } else {
            result = "error";
        }

        return "redirect:result?"+result+"=true";

    }
}
