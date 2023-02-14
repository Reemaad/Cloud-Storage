package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;

    @GetMapping("/home")
    public String homeView(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        List<File> userFiles = fileService.getUserFiles(authentication.getName());
        List<Note> userNotes = noteService.getUserNotes(authentication.getName());
        model.addAttribute("files", userFiles);
        model.addAttribute("notes", userNotes);
        return "home";
    }
}
