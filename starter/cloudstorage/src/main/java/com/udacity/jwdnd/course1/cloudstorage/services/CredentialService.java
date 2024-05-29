package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper mapper, EncryptionService encryptionService) {
        this.credentialMapper = mapper;
        this.encryptionService = encryptionService;
    }

    public String decryptCredential(Credential credential) {
        var found = credentialMapper.retrieve(credential);
        return encryptionService.decryptValue(found.getPassword(), found.getKey());
    }

    public List<Credential> fetchAllByUserId(String UID) {
        return credentialMapper.fetchAllByUserId(UID);
    }

    public void removeCredential(Credential credential) {
        credentialMapper.remove(credential);
    }

    public void saveCredential(Credential credential) {
        if (credential.getId() == null) {
            var key = generateEncryptionKey();
            var encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);


            credentialMapper.save(
                new Credential(
                    null,
                    key,
                    credential.getUrl(),
                    credential.getUsername(),
                    encryptedPassword,
                    credential.getUserId()
                )
            );
            return;
        }

        var key = credentialMapper.retrieve(credential).getKey();
        var rawPassword = credential.getPassword();
        credential.setPassword(encryptionService.encryptValue(rawPassword, key));
        credentialMapper.modify(credential);
    }

    private String generateEncryptionKey() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

}