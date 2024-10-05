package org.example.utils;

public class JwtUtils {
    private static String jwtToken;

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static String extractJwtFromResponse(String responseBody) {
        return responseBody;
    }
}
