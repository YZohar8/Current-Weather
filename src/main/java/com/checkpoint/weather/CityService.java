package com.checkpoint.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CityService {

    private static final String CITY_URL = 
        "http://weather-automation-checkpoint-task.westeurope.cloudapp.azure.com:3000/cities";

    public List<String> fetchCities() throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(CITY_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);

            if (response.getCode() != 200) {
                throw new RuntimeException("Failed to fetch cities, HTTP code: " + response.getCode());
            }

            ObjectMapper mapper = new ObjectMapper();
            CitiesResponse citiesResponse = mapper.readValue(response.getEntity().getContent(), CitiesResponse.class);

            return citiesResponse.getCities().stream()
                    .map(City::getName)
                    .filter(name -> name != null && !name.isBlank())
                    .distinct()
                    .collect(Collectors.toList());
        }
    }
}
