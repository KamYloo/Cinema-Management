package org.example.showTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.dto.MovieDto;
import org.example.dto.ShowTimeDto;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;
import org.example.utils.LocalDateTimeAdapter;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowTimeService {
    private static final String MOVIE_ENDPOINT = "/api/showTimes";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static List<ShowTimeDto> getShowTimesByMovie(Integer movieId) throws Exception {
        HttpResponse<String> response = HttpUtils.get(MOVIE_ENDPOINT + "/movie/"+ movieId, JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {
            Type showTimeArrayListType = new TypeToken<ArrayList<ShowTimeDto>>(){}.getType();
            return gson.fromJson(response.body(), showTimeArrayListType);
        } else {
            throw new RuntimeException("Error fetching all showTimes: " + response.body());
        }
    }
}
