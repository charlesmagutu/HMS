package com.condabu.userservice.dto;

import com.condabu.userservice.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDto extends CrudRepository<Role, String> {
}
