package com.checkpoint.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CityServiceTest {

    @Test
    public void testFetchCitiesNotEmpty() throws Exception {
        CityService cityService = new CityService();
        List<String> cities = cityService.fetchCities();
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertTrue(cities.contains("Tokyo"));
    }

    
}
