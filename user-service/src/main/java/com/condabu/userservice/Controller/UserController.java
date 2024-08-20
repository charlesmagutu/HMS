package com.condabu.userservice.Controller;

import com.condabu.userservice.Entity.AuthRequest;
import com.condabu.userservice.Entity.User;
import com.condabu.userservice.Service.UserService;
import com.condabu.userservice.common.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired

    private UserService userService;

    @PostMapping
    public ResponseEntity<ServiceResponse> createNewUser(@RequestBody User user) {
        try{
            userService.createNewUser(user);
            return ResponseEntity.ok(new ServiceResponse("success","Account created successfully with username"+ user.getUsername()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ServiceResponse("failed", "Failed to create account: "+e.getMessage()));
        }
    }

    @GetMapping
    public List<User> listAllUsers(){
       return userService.getAllUsers();
    }

    @GetMapping("{username}")

    public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username){
        Optional<User> user =  userService.findUserByUserName(username);

        if (user.isPresent()){
            return  ResponseEntity.ok(user.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "User with username "+ username +" no found"));
        }
    }


    @PostMapping("/verify")

    public ResponseEntity<Boolean> verifyUserCredentials(@RequestBody AuthRequest authRequest){
        boolean isValid = userService.verifyCredentials(authRequest.getUsername(), authRequest.getPassword());

        return ResponseEntity.ok(isValid);
    }


}
