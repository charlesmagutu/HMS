package com.condabu.userservice.dto;

import com.condabu.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDto extends CrudRepository<User, String> {

}
