package com.checkpoint.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class WeatherService {

    private static final String API_KEY_URL =
            "http://weather-automation-checkpoint-task.westeurope.cloudapp.azure.com:3000/privateKey_shh";
    private static final String WEATHER_API_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    private final Random random = new Random();
    private final ObjectMapper mapper = new ObjectMapper();
    private String apiKey;

    public WeatherService() throws IOException {
        this.apiKey = fetchApiKey();
    }

    private String fetchApiKey() throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_KEY_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);

            if (response.getCode() != 200) {
                throw new RuntimeException("Failed to fetch API key, HTTP code: " + response.getCode());
            }

            JsonNode node = mapper.readTree(response.getEntity().getContent());
            return node.get("key").asText();
        }
    }

    public double getTemperatureForCity(String city) throws IOException, InterruptedException {
        Thread.sleep(random.nextInt(1000));
        String cityEncoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format(WEATHER_API_URL, cityEncoded, apiKey);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);

            if (response.getCode() == 429) {
                // Rate limit exceeded, wait a bit and retry once
                Thread.sleep(1000 + random.nextInt(2000));
                return getTemperatureForCity(city);
            }

            if (response.getCode() != 200) {
                throw new RuntimeException("Failed to fetch weather for " + city + ", HTTP code: " + response.getCode());
            }

            JsonNode root = mapper.readTree(response.getEntity().getContent());
            return root.get("main").get("temp").asDouble();
        }
    }
}
