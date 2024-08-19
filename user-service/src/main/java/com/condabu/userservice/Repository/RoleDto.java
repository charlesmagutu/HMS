package com.condabu.userservice.Repository;

import com.condabu.userservice.Entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDto extends CrudRepository<Role, String> {
}
