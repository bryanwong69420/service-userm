
package com.mcp.userManagement.config.security;

import com.mcp.userManagement.constants.RedisDbKeys;
import com.redis.redisutil.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RedisUtil redisUtil;

    private static final List<String> WHITELISTED_ENDPOINTS = List.of(
            "/api/v1/internal/[a-zA-Z0-9]+"
    );

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if (this.isWhitelistedEndpoint(requestUri)) {
            return true;
        }

        String token = getJwtFromRequest(request);
        if (token != null && this.tokenProvider.validateJwtToken(token)) {
            String key = RedisDbKeys.getRedisCacheTokenKey(token);
            boolean isValidToken = this.redisUtil.hasKey(key).booleanValue();
            if (isValidToken) {
                String userLastActKey = RedisDbKeys.getRedisCacheUserLastActivityKey(token);
                this.redisUtil.setCacheObject(userLastActKey, System.currentTimeMillis());
                return true;
            }
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token has been invalidated.");
            return false;
        }
        response.sendError(401, "Unauthorized");
        return false;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7);
        return null;
    }

    private boolean isWhitelistedEndpoint(String requestUri) {
        return WHITELISTED_ENDPOINTS.stream().anyMatch(requestUri::matches);
    }
}
