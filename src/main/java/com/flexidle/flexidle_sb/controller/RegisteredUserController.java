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

    /**
     * Checks if a username exists in the database (table: registered_user).
     *
     * @param username      String, the username that should be verified.
     *
     * @return              boolean, represents whether the username exists in db or not.
     *
     * @author Frida Sjögren
     */
    @GetMapping("/checkUsername/{username}")
    public boolean checkUsername(@PathVariable String username) {
        RegisteredUser registeredUser = registeredUserService.getRegisteredUserByUsername(username);
        if (registeredUser != null) {
            System.out.println("DEBUG username '" + username + "' finns!");
            return true;
        } else {
            System.out.println("DEBUG username '" + username + "' finns inte!");
            return false;
        }
    }

    /**
     * Checks if a password is correct for a given username stored in the database (table:
     * registered_user). If the given username doesn't exist, false is returned.
     *
     * @param password      String, the password that is checked.
     * @param username      String, the username that the password is matched to.
     *
     * @return              boolean, represents whether the password and username match or not.
     */
    @GetMapping("/checkPassword/{password}/{username}")
    public boolean checkPassword(@PathVariable String password, @PathVariable String username) {
        RegisteredUser registeredUser = registeredUserService.getRegisteredUserByUsername(username);
        if (registeredUser == null) {
            return false;
        }

        if (registeredUser.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a new registered user in the database. Used in frontend, the registeredUser object
     * is sent as a JSON. The input is checked in frontend before this method is called.
     *
     * @param registeredUser    RegisteredUser, a new user account to be created.
     *
     * @return                  returns the new registeredUser.
     *
     * @author Frida Sjögren
     */
    @PostMapping("/createAccount")
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
