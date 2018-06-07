package com.din.testhttp.weather;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityWeatherBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dinzhenyan on 2018/4/24.
 */

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding bind;
    private static final String WEATHER = "https://www.apiopen.top/weatherApi?city=赣州";
    private static final String BACKGROUND_PICTURE = "http://guolin.tech/api/bing_pic";

    private WeatherAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        layoutManager = new LinearLayoutManager(this);
        bind.recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(this);
        bind.recyclerView.setItemAnimator(new DefaultItemAnimator());
        bind.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new MyAsyncTask().execute(WEATHER);
                new PictureAsyncTask().execute(BACKGROUND_PICTURE);
            }
        });
        new PictureAsyncTask().execute(BACKGROUND_PICTURE);
        // 异步加载数据
        bind.swipeRefresh.setRefreshing(true);
        new MyAsyncTask().execute(WEATHER);
    }

    /**
     * 异步请求，返回一个JSON字符串string
     */
    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String jsonData = null;
            try {
                Response response = sendHttpRequset(strings[0]);
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getResponse(s);
        }
    }

    /**
     * 异步请求，返回一个图片地址string
     */
    private class PictureAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String picUrl = null;
            try {
                Response response = sendHttpRequset(strings[0]);
                picUrl = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return picUrl;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Glide.with(WeatherActivity.this).load(s).into(bind.image);
            if (bind.swipeRefresh.isRefreshing()) {
                bind.swipeRefresh.setRefreshing(false);
            }
        }
    }

    /**
     * HTTP请求
     *
     * @param url
     * @return 返回一个response
     */
    private Response sendHttpRequset(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gson数据解析
     *
     * @param jsonData
     */
    private void getResponse(String jsonData) {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new StringReader(jsonData));
        jsonReader.setLenient(true);
        WeatherData weatherData = gson.fromJson(jsonReader, new TypeToken<WeatherData>() {
        }.getType());
        if (weatherData.getCode() == 200) {     // 判断结果是否返回成功
            showWeatherInfo(weatherData);
        } else {
            Toast.makeText(WeatherActivity.this, "获取数据为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将数据显示出来
     *
     * @param weatherData
     */
    private void showWeatherInfo(WeatherData weatherData) {
        List<Weather> list = new ArrayList<>();
        List<WeatherHead> listHead = new ArrayList<>();
        String wendu = weatherData.getData().getWendu();
        String ganmao = weatherData.getData().getGanmao();
        String city = weatherData.getData().getCity();
        listHead.add(new WeatherHead(wendu, city, ganmao));
        for (WeatherData.DataBean.ForecastBean forecast : weatherData.getData().getForecast()) {
            String date = forecast.getDate();
            String high = forecast.getHigh();
            String fengli = forecast.getFengli();
            String low = forecast.getLow();
            String fengxiang = forecast.getFengxiang();
            String type = forecast.getType();
            list.add(new Weather(date, high, fengli, low, fengxiang, type));
        }
        // 设置RecyclerView相关
        adapter.notifyData(list, listHead);
        adapter.notifyDataSetChanged();
        bind.recyclerView.setAdapter(adapter);
        if (bind.swipeRefresh.isRefreshing()) {
            bind.swipeRefresh.setRefreshing(false);
        }
    }
}