package com.din.testhttp.weather;

/**
 * Created by dinzhenyan on 2018/4/24.
 */

public class Weather {
    private String date;
    private String fenli;
    private String hight;
    private String low;
    private String fengxiang;
    private String type;

    public Weather(String date, String hight, String fenli, String low, String fengxiang, String type) {
        this.date = date;
        this.hight = hight;
        this.fenli = fenli;
        this.low = low;
        this.fengxiang = fengxiang;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFenli() {
        return fenli;
    }

    public void setFenli(String fenli) {
        this.fenli = fenli;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}