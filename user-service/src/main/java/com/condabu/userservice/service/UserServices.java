package com.condabu.userservice.service;

import com.condabu.userservice.dto.UserDto;
import com.condabu.userservice.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserDto userDto;

    @Transactional
    public User createNewUser(User user) {

        return userDto.save(user);
    }
}
