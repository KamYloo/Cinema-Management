package org.example.utils;

import com.google.gson.Gson;
import org.example.auth.LoginResponse;

public class JwtUtils {
    private static String jwtToken;

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static String extractJwtFromResponse(String responseBody) {
        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(responseBody, LoginResponse.class);
        return loginResponse.getJwt();
    }
}
