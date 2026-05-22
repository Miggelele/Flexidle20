package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.RegisteredUser;
import com.flexidle.flexidle_sb.model.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registered_user")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @GetMapping("")
    public List<RegisteredUser> getAllRegisteredUsers() {
        return registeredUserService.getAllRegisteredUsers();
    }

    @GetMapping("/{username}")
    public RegisteredUser getRegisteredUserByUsername(@PathVariable String username) {
        return registeredUserService.getRegisteredUserByUsername(username);
    }

    @PostMapping("")
    public RegisteredUser createRegisteredUser(@RequestBody RegisteredUser registeredUser) {
        return registeredUserService.createRegisteredUser(registeredUser);
    }

    @PutMapping("/{username}")
    public RegisteredUser updateRegisteredUser(@PathVariable String username, @RequestBody RegisteredUser registeredUser) {
        return registeredUserService.updateRegisteredUser(username, registeredUser);
    }

    @DeleteMapping("/{username}")
    public void deleteRegisteredUser(@PathVariable String username) {
        registeredUserService.deleteRegisteredUser(username);
    }
}
