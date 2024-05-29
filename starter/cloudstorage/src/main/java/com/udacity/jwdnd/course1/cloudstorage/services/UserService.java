package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameFree(String username) {
        return userMapper.fetchUser(username) == null;
    }

    public int createUser(User user) {
        String encodedSalt = generateSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.save(
            new User(
                null,
                user.getUsername(),
                encodedSalt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName()
            )
        );
    }

    public User fetchUser(String username) {
        return userMapper.fetchUser(username);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

}