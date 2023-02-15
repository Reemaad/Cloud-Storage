package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(String username) {
        User user = userMapper.getUser(username);
        return credentialMapper.getUserCredentials(user.getUserId());
    }

    public int addCredential(Credential credential, String username) {
        User user = userMapper.getUser(username);
        credential.setKey(getEncryptionKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), user.getUserId()));
    }

    public void updateCredential(Credential credential) {
        credential.setKey(getEncryptionKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        credentialMapper.updateCredential(credential);
    }

    public boolean isCredentialAlreadyExist(Integer credentialId) {
        return credentialMapper.getCredential(credentialId) != null;
    }

    public boolean deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }


    public String getDecryptedPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encryptionService.decryptValue(password, encodedKey);
    }

    private String getEncryptionKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
