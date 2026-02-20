package com.mcp.userManagement.service.impl;

import com.mcp.userManagement.config.AppProperties;
import com.mcp.userManagement.model.RefreshToken;
import com.mcp.userManagement.model.Users;
import com.mcp.userManagement.repository.RefreshTokenRepository;
import com.mcp.userManagement.repository.UserRepository;
import com.mcp.userManagement.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppProperties appProperties;

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() ->  new RuntimeException("No User Found with ID: " + userId));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpDate(LocalDateTime.now().plusSeconds(appProperties.getAuth().getRefreshExpiration() / 1000));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        // Check if the token expiration date is before the current time
        if (token.getExpDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            return null;
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }
}
