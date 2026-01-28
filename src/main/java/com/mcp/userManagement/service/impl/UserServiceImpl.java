package com.mcp.userManagement.service.impl;

import com.mcp.userManagement.dto.request.RegisterUserDTO;
import com.mcp.userManagement.dto.vo.SignUpRequestVo;
import com.mcp.userManagement.dto.vo.UserVo;
import com.mcp.userManagement.enums.ERole;
import com.mcp.userManagement.model.Role;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.repository.RoleRepository;
import com.mcp.userManagement.repository.UserRepository;
import com.mcp.userManagement.service.UserService;
import com.mcp.userManagement.utils.SnowflakeIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
    public boolean signUp(SignUpRequestVo dto) {
        try {
            Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER.getKey());
            Set<Role> roleSet = userRole.map(Set::of).orElse(Collections.emptySet());
            Users users = Users.builder()
                    .id(SnowflakeIdGenerator.generateId())
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .email("testingemail@gmail.com")
                    .roles(roleSet)
                    .build();
            userRepository.save(users);
            log.info("User Sign Up Success!");
            return true;
        } catch (Exception e) {
            log.error("User Sign Up Failed: {}", e.getMessage());
            return false;
        }
    }

    private UserVo mapperUserVo(Users user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Users.class, UserVo.class).addMappings(mapper -> {
            mapper.map(Users::getRoles, UserVo::setRoles);
        });

        return modelMapper.map(user, UserVo.class);
    }
}
