package com.mcp.userManagement.service;

import com.mcp.userManagement.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    Optional<RefreshToken> findByRefreshToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
}
