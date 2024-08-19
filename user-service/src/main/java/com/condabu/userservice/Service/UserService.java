package com.condabu.userservice.Service;

import com.condabu.userservice.Repository.UserDto;
import com.condabu.userservice.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final  UserDto userDto;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDto userDto, PasswordEncoder passwordEncoder) {
        this.userDto = userDto;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createNewUser(User user) {
        String encodePassword =  passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userDto.save(user);
    }

    public boolean verifyCredentials(String username, String password){
        Optional<User> user = userDto.findByUsername(username);

        if(user.isPresent()){
            User status = user.get();
            return passwordEncoder.matches(password, status.getPassword());
        }
        return false;
    }
}
