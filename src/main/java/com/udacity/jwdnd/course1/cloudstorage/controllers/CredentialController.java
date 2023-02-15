package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CredentialController {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/submitCredential")
    public String submitCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model) {
        String result = null;

        if (credentialService.isCredentialAlreadyExist(credential.getCredentialId())) {
            credentialService.updateCredential(credential);
            result = "success";
        } else {
            int rowNumber = credentialService.addCredential(credential, authentication.getName());
            if (rowNumber < 0) {
                result = "error";
            } else {
                result = "success";
            }
        }

        return "redirect:result?" + result + "=true";
    }

    @GetMapping("/deleteCredential")
    public String deleteFile(@RequestParam("credentialId") Integer credentialId, Authentication authentication, Model model) {
        String result = null;

        boolean isCredentialDeletedSuccessfully = credentialService.deleteCredential(credentialId);

        if (isCredentialDeletedSuccessfully) {
            result = "success";
        } else {
            result = "error";
        }

        return "redirect:result?" + result + "=true";
    }


}
