package com.din.testhttp.express;

/**
 * Created by dinzhenyan on 2018/3/31.
 */

public class Express {
    private String time;
    private String context;

    public Express(String time, String context) {
        this.time = time;
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
