package org.example.reservation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.dto.ReservationDto;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;
import org.example.utils.LocalDateTimeAdapter;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private static final String RESERVATION_ENDPOINT = "/api/reservations";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static ReservationDto createReservation(Integer seatId) throws Exception {
        String jsonBody = gson.toJson(Map.of("seatId", seatId));
        HttpResponse<String> response = HttpUtils.postWithToken(RESERVATION_ENDPOINT + "/create", jsonBody, JwtUtils.getJwtToken());
        return gson.fromJson(response.body(), ReservationDto.class);
    }

    public static List<ReservationDto> getReservationsByUser(Integer userId) throws Exception {
        HttpResponse<String> response = HttpUtils.get(RESERVATION_ENDPOINT + "/user/" + userId, JwtUtils.getJwtToken());
        if (response.statusCode() == 200) {
            Type reservationListType = new TypeToken<List<ReservationDto>>() {}.getType();
            return gson.fromJson(response.body(), reservationListType);
        } else {
            throw new RuntimeException("Error fetching reservations: " + response.body());
        }
    }

    public static void deleteReservation(Integer reservationId) throws Exception {
        HttpResponse<String> response = HttpUtils.delete(RESERVATION_ENDPOINT + "/delete/" + reservationId, JwtUtils.getJwtToken());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error deleting reservation: " + response.body());
        }
    }
}
