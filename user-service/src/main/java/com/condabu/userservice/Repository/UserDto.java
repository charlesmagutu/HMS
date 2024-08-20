package com.condabu.userservice.Repository;

import com.condabu.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDto extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
   @Override
    List<User> findAll();


}
