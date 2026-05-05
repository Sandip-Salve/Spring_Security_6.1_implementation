package com.app.security.controller;

import com.app.security.entities.Role;
import com.app.security.service.IRoleService;
import com.app.security.utilities.CustomApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse> createNewRole(@RequestBody Role role){
        CustomApiResponse customApiResponse = roleService.createNewRole(role);
        return new ResponseEntity<>(customApiResponse, HttpStatus.OK);
    }
}
