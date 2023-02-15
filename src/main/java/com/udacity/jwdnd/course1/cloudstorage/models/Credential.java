package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;

}
