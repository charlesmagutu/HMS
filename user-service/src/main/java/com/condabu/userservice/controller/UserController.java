package com.condabu.userservice.controller;

import com.condabu.userservice.entity.User;
import com.condabu.userservice.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired

    private UserServices userServices;

    @PostMapping
    public User createNewUser(@RequestBody User user) {

        return userServices.createNewUser(user);
    }
}
