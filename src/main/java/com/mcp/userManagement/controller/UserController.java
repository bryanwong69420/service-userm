package com.mcp.userManagement.controller;

import com.mcp.userManagement.dto.request.RegisterUserDTO;
import com.mcp.userManagement.dto.response.ApiDTO;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.repository.UserRepository;
import com.mcp.userManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterUserDTO dto) {
        if (!dto.getPassword().equals(dto.getMatchingPassword())) {
            return new ResponseEntity<>(new ApiDTO(false, "Non Matching Password"), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.createUser(dto);
            return new ResponseEntity<>(new ApiDTO(true, "User Created Successfully"), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiDTO(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
