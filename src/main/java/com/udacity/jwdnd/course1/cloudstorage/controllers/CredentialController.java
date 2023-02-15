package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/submitCredential")
    public String submitCredential(@ModelAttribute("credential") Credential credential, Authentication authentication) {
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
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId) {
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
