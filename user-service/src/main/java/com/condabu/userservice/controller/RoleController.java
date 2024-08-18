package com.condabu.userservice.controller;

import com.condabu.userservice.entity.Role;
import com.condabu.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {

    @Autowired
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }


    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id) {

        boolean isDeleted = roleService.deleteRole(id);
        if(isDeleted){
            return ResponseEntity.ok("Role deleted successfully.");
        }

        return ResponseEntity.status(400).body("Role could not be deleted.");
    }
}
