package com.mcp.userManagement.service.impl;

import com.mcp.userManagement.dto.request.RegisterUserDTO;
import com.mcp.userManagement.dto.vo.UserVo;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.repository.UserRepository;
import com.mcp.userManagement.service.UserService;
import com.mcp.userManagement.utils.SnowflakeIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<UserVo> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapperUserVo);
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }

    @Override
    public void createUser(RegisterUserDTO dto) {
        Users users = Users.builder()
                .id(SnowflakeIdGenerator.generateId())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email("testingemail@gmail.com")
                .build();

        userRepository.save(users);
    }

    private UserVo mapperUserVo(Users user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Users.class, UserVo.class).addMappings(mapper -> {
            mapper.map(Users::getRoles, UserVo::setRoles);
        });

        return modelMapper.map(user, UserVo.class);
    }
}
