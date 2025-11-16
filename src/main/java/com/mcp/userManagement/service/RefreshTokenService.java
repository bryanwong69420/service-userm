package com.mcp.userManagement.service;

import com.mcp.userManagement.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
}
