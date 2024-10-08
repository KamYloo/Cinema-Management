package org.example.reservation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dto.MovieDto;
import org.example.dto.ReservationDto;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;
import org.example.utils.LocalDateTimeAdapter;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;

public class ReservationService {
    private static final String MOVIE_ENDPOINT = "/api/reservations";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static ReservationDto createReservation(Integer seatId) throws Exception {
        String jsonBody = gson.toJson(Map.of("seatId", seatId));
        HttpResponse<String> response = HttpUtils.postWithToken(MOVIE_ENDPOINT + "/create", jsonBody, JwtUtils.getJwtToken());
        return gson.fromJson(response.body(), ReservationDto.class);
    }
}
