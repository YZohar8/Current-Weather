package com.checkpoint.weather;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherServiceTest {

    @Test
    void testGetTemperatureForCity() throws IOException, InterruptedException {
        WeatherService ws = new WeatherService();
        double temp = ws.getTemperatureForCity("Cairo");
        assertTrue(temp > -50 && temp < 60, "Temperature is in a reasonable range");
    }
}
