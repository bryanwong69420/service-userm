package com.mcp.userManagement.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestVo {
    private String username;
    private String email;
    private String password;
    private String matchingPassword;
}
