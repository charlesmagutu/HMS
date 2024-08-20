package com.condabu.userservice.Service;

import com.condabu.userservice.Repository.UserDto;
import com.condabu.userservice.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void createNewUser(User user) {
        String encodePassword =  passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        userDto.save(user);
    }

    public boolean verifyCredentials(String username, String password){
        Optional<User> user = userDto.findByUsername(username);

        if(user.isPresent()){
            User status = user.get();
            return passwordEncoder.matches(password, status.getPassword());
        }
        return false;
    }

    public List<User> getAllUsers(){
        return userDto.findAll();
    }


    public Optional<User> findUserByUserName(String username){
        return userDto.findByUsername(username);
    }



}
