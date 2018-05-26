package com.din.testhttp.gson;

/**
 * Created by dinzhenyan on 2018/3/31.
 */

public class Weather {
    private String name;
    private String text;
    private int code;
    private int temperature;

    public Weather(String name, String text, int code, int temperature) {
        this.name = name;
        this.text = text;
        this.code = code;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}