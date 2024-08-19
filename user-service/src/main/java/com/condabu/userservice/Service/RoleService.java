package com.condabu.userservice.Service;

import com.condabu.userservice.Repository.RoleDto;
import com.condabu.userservice.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDto roleDto;
    public Role createNewRole(Role role){
        return roleDto.save(role);
    }

    public List<Role> getAllRoles(){
       return (List<Role>) roleDto.findAll();
    }

    public boolean deleteRole(Long id){
        if(roleDto.existsById(String.valueOf(id))){
            roleDto.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }
}
