package com.flexidle.flexidle_sb.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserRepository.findAll();
    }

    public RegisteredUser getRegisteredUserByUsername(String username) {
        return registeredUserRepository.findById(username).orElse(null);
    }

    public RegisteredUser createRegisteredUser(RegisteredUser newRegisteredUser) {
        return registeredUserRepository.save(newRegisteredUser);
    }

    public RegisteredUser updateRegisteredUser(String username, RegisteredUser newRegisteredUser) {
        RegisteredUser existingRegisteredUser = registeredUserRepository.findById(username).orElse(null);

        if (existingRegisteredUser != null) {
            existingRegisteredUser.setPassword(newRegisteredUser.getPassword());
            existingRegisteredUser.setS_question(newRegisteredUser.getS_question());
            existingRegisteredUser.setS_answer(newRegisteredUser.getS_answer());
            return registeredUserRepository.save(existingRegisteredUser);
        } else {
            return null;
        }
    }

    public void deleteRegisteredUser(String username) {
        registeredUserRepository.deleteById(username);
    }
}
