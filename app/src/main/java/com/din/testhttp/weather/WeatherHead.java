package com.din.testhttp.weather;

/**
 * Created by dinzhenyan on 2018/4/24.
 */

public class WeatherHead {
    private String temperature;
    private String info;
    private String city;

    public WeatherHead(String temperature, String city, String info) {
        this.temperature = temperature;
        this.city = city;
        this.info = info;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
