package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String homeView(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Authentication authentication, Model model) {
        List<File> userFiles = fileService.getUserFiles(authentication.getName());
        List<Note> userNotes = noteService.getUserNotes(authentication.getName());
        List<Credential> userCredentials = credentialService.getUserCredentials(authentication.getName());
        model.addAttribute("files", userFiles);
        model.addAttribute("notes", userNotes);
        model.addAttribute("credentials", userCredentials);

        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}
