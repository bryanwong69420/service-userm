package com.mcp.userManagement.controller;

import com.mcp.userManagement.config.security.TokenProvider;
import com.mcp.userManagement.constants.ApiConstants;
import com.mcp.userManagement.dto.request.RegisterUserDTO;
import com.mcp.userManagement.dto.response.ApiDTO;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.repository.UserRepository;
import com.mcp.userManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.USER_BASE_URL)
public class UserController {
    private final UserService userService;

    @GetMapping("/test-access-token")
    public ResponseEntity<?> testAuth() {
        return new ResponseEntity<>(new ApiDTO(true, "Authorized"), HttpStatus.OK);
    }
}
