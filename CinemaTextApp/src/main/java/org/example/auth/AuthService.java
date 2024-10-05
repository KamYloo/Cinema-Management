package org.example.auth;

import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;

import java.net.http.HttpResponse;

public class AuthService {
    public static boolean login(String email, String password) throws Exception {
        String loginJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        HttpResponse<String> response = HttpUtils.post("/auth/login", loginJson);
        if (response.statusCode() == 200) {
            String jwt = JwtUtils.extractJwtFromResponse(response.body());
            JwtUtils.setJwtToken(jwt);
            return true;
        }
        return false;
    }

    public static boolean register(String fullName, String email, String password, String role) throws Exception {
        String registerJson = String.format("{\"fullName\":\"%s\", \"email\":\"%s\", \"password\":\"%s\", \"role\":\"%s\"}", fullName, email, password, role);

        HttpResponse<String> response = HttpUtils.post("/auth/register", registerJson);
        return response.statusCode() == 200;
    }
}
