package com.mcp.userManagement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcp.userManagement.dto.vo.UserVo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class CommonUtils {
    public static String convertToSnakeCase(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input.trim().toLowerCase().replace(" ", "_");
    }

    public static boolean isValid(String password, String passwordRegex) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return password.trim().matches(passwordRegex);
    }

    public static String encodeUserVo(UserVo userVo) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonBytes = null;
        try {
            jsonBytes = mapper.writeValueAsBytes(userVo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gzip = new GZIPOutputStream(byteArrayOutputStream)) {
                gzip.write(jsonBytes);
            }
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

}
