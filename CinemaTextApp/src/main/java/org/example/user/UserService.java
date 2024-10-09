package org.example.user;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.dto.UserDto;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;

public class UserService {
    public static boolean isAdmin() throws Exception {
        String token = JwtUtils.getJwtToken();
        System.out.println(token);
        String response = HttpUtils.get("/api/users/profile", token).body();
        System.out.println("Response from /users/profile: " + response);
        if (response.startsWith("{")) {
            Gson gson = new Gson();
            try {
                UserDto user = gson.fromJson(response, UserDto.class);
                return user.isAdmin();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse response as UserDto");
            }
        } else {
            throw new RuntimeException("Unexpected response format: " + response);
        }
    }

    public static Integer getLoggedInUserId() throws Exception {
        String token = JwtUtils.getJwtToken();
        String response = HttpUtils.get("/api/users/profile", token).body();
        if (response.startsWith("{")) {
            Gson gson = new Gson();
            try {
                UserDto user = gson.fromJson(response, UserDto.class);
                return user.getId();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse response as UserDto");
            }
        } else {
            throw new RuntimeException("Unexpected response format: " + response);
        }
    }
}
