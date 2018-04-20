package com.din.testhttp.gson;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by dinzhenyan on 2018/3/30.
 */

public class GSONObjects {

    private TextView textView;
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void GSON(String gsonData) {
        Gson gson = new Gson();
        List<Weather> weathers = gson.fromJson(gsonData, new TypeToken<List<Weather>>() {
        }.getType());
        for (Weather weather : weathers) {
            textView.append("\n  getTemp is : " + weather.getTemp());
            textView.append("\n  getWind_direction is : " + weather.getWind_direction());
            textView.append("\n  getWind_strength is : " + weather.getWind_strength());
            textView.append("\n  getHumidity is : " + weather.getHumidity());
            textView.append("\n  getTime is : " + weather.getTime());

        }
    }
}
