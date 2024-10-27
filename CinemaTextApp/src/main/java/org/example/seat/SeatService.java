package org.example.seat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.dto.SeatDto;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;
import org.example.utils.LocalDateTimeAdapter;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeatService {
    private static final String MOVIE_ENDPOINT = "/api/seats";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static List<SeatDto> getSeatsByShowTime(Integer showTimeId) throws Exception {
        HttpResponse<String> response = HttpUtils.get(MOVIE_ENDPOINT + "/showtime/"+ showTimeId, JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {
            Type seatsArrayListType = new TypeToken<ArrayList<SeatDto>>(){}.getType();
            return gson.fromJson(response.body(), seatsArrayListType);
        } else {
            throw new RuntimeException("Error fetching all seats: " + response.body());
        }
    }
}
