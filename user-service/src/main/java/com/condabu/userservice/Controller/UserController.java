package com.condabu.userservice.Controller;

import com.condabu.userservice.Entity.AuthRequest;
import com.condabu.userservice.Entity.User;
import com.condabu.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired

    private UserService userService;

    @PostMapping
    public User createNewUser(@RequestBody User user) {

        return userService.createNewUser(user);
    }

    @PostMapping("/verify")

    public ResponseEntity<Boolean> verifyUserCredentials(@RequestBody AuthRequest authRequest){
        boolean isValid = userService.verifyCredentials(authRequest.getUsername(), authRequest.getPassword());

        return ResponseEntity.ok(isValid);
    }


}
