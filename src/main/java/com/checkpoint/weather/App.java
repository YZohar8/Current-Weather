package com.checkpoint.weather;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        try {
            CityService cityService = new CityService();
            WeatherService weatherService = new WeatherService();
            ExcelWriter excelWriter = new ExcelWriter();

            List<String> cities = cityService.fetchCities();

            ExecutorService executor = Executors.newFixedThreadPool(5);

            List<Callable<CityTemperature>> tasks = new ArrayList<>();
            for (String city : cities) {
                tasks.add(() -> {
                    double temp = weatherService.getTemperatureForCity(city);
                    return new CityTemperature(city, temp);
                });
            }

            List<Future<CityTemperature>> futures = executor.invokeAll(tasks);

            List<CityTemperature> results = new ArrayList<>();
            for (Future<CityTemperature> future : futures) {
                try {
                    results.add(future.get());
                } catch (ExecutionException e) {
                    System.err.println("Error fetching weather: " + e.getCause().getMessage());
                }
            }

            executor.shutdown();

            results.sort(Comparator.comparingDouble(CityTemperature::getTemperature).reversed());

            System.out.println("Weather results:");
            for (CityTemperature ct : results) {
                System.out.printf("%s : %.1f°C%n", ct.getCity(), ct.getTemperature());
            }


            File excelDir = new File("Excel");
            if (!excelDir.exists()) {
                excelDir.mkdir();
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String filePath = "Excel/weather_results_" + timestamp + ".xlsx";            excelWriter.writeWeatherToExcel(results, filePath);
            System.out.println("\nExcel file created: " + filePath);

        } catch (IOException | InterruptedException e) {
            System.err.println("Fatal error: " + e.getMessage());
        }
    }

}
