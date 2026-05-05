package com.app.security.controller;

import com.app.security.dtos.UserDto;
import com.app.security.service.IUserService;
import com.app.security.utilities.CustomApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<CustomApiResponse> createNewUser(@RequestBody UserDto userDto){
        CustomApiResponse response = userService.createNewUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
