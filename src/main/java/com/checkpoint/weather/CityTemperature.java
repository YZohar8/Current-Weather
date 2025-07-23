package com.checkpoint.weather;

public class CityTemperature {
    private final String city;
    private final double temperature;

    public CityTemperature(String city, double temperature) {
        this.city = city;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }
}
