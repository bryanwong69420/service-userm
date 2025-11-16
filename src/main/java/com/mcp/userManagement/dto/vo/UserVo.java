package com.mcp.userManagement.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserVo implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private LocalDateTime last_login_at;
    private RefreshTokenVo refreshTokenVo;
}
