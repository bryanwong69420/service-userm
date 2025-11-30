package com.mcp.userManagement.controller;


import com.mcp.userManagement.config.security.TokenProvider;
import com.mcp.userManagement.constants.ApiConstants;
import com.mcp.userManagement.dto.vo.RefreshTokenVo;
import com.mcp.userManagement.dto.vo.UserVo;
import com.mcp.userManagement.dto.vo.UsernameVo;
import com.mcp.userManagement.model.RefreshToken;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.service.RefreshTokenService;
import com.mcp.userManagement.service.UserService;
import com.mcp.userManagement.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.INTERNAL_BASE_URL)
public class UserInternalController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(ApiConstants.GET_USER_BY_USERNAME_ENDPOINT)
    public ResponseEntity<?> getUserByUsername(@RequestBody UsernameVo request) {
        String username = request.getUsername();
        if (ObjectUtils.isEmpty(username)) {
            log.error("Username cannot be empty.");
            return ResponseEntity.badRequest().body("Username cannot be empty!");
        }
        Optional<UserVo> optionalUser = userService.findByUsername(username);

        if (optionalUser.isEmpty()) {
            log.error("User not found.");
            return ResponseEntity.badRequest().body("User not found!");
        }

        UserVo userVo = optionalUser.get();
        // Update Last Login Date
        Optional<Users> optUser = userService.findById(userVo.getId());
        if (optUser.isPresent()) {
            Users user = optUser.get();
            user.setLastLoginAt(LocalDateTime.now());
            userService.save(user);
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userVo.getId());
        ModelMapper modelMapper = new ModelMapper();
        userVo.setRefreshTokenVo(modelMapper.map(refreshToken, RefreshTokenVo.class));
        String encodeContent = CommonUtils.encodeUserVo(userVo);
        return new ResponseEntity<>(encodeContent, HttpStatus.OK);

    }
}
