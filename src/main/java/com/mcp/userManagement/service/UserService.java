package com.mcp.userManagement.service;

import com.mcp.userManagement.dto.request.RegisterUserDTO;
import com.mcp.userManagement.dto.vo.SignUpRequestVo;
import com.mcp.userManagement.dto.vo.UserVo;
import com.mcp.userManagement.model.Users;

import java.util.Optional;

public interface UserService {
    Optional<UserVo> findByUsername(String username);

    Optional<Users> findById(Long id);

    void save(Users user);

    boolean signUp(SignUpRequestVo vo);

    UserVo mapperVo(Users user);

}
