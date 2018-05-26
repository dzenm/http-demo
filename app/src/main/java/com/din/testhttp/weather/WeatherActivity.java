package com.din.testhttp.weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityWeatherBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private static final String URL = "https://www.apiopen.top/weatherApi?city=%E6%8F%AD%E9%98%B3";
    private List<Weather> list;
    private List<WeatherHead> listHead;
    private WeatherAdapter adapter;
    private boolean isLoadingMore = true;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        list = new ArrayList<>();
        listHead = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        bind.recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherAdapter(this, list, listHead);
        bind.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
                bind.recyclerView.setAdapter(adapter);
            }
        });

        //  解决嵌套滑动的不流畅
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        bind.recyclerView.setLayoutManager(layoutManager);
        bind.recyclerView.setHasFixedSize(true);
        bind.recyclerView.setNestedScrollingEnabled(false);

        //
        bind.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if (isLoadingMore) {

                    } else {
//                        loadPage();//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = false;
                    }
                }
            }
        });
    }

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(URL).build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    getResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getResult(String result) {
        try {
            JSONObject codeJSON = new JSONObject(result);
            String code = codeJSON.getString("code");
            if (code.equals("200")) {
                JSONObject dataJSON = codeJSON.getJSONObject("data");

                String ganmao = dataJSON.getString("ganmao");
                String wendu = dataJSON.getString("wendu");
                String city = dataJSON.getString("city");
                listHead.add(new WeatherHead(wendu, city, ganmao));
                JSONArray forecastArray = dataJSON.getJSONArray("forecast");
                for (int i = 0; i < forecastArray.length(); i++) {
                    JSONObject forecastJSON = forecastArray.getJSONObject(i);
                    String date = forecastJSON.getString("date");
                    String high = forecastJSON.getString("high");
                    String fengli = forecastJSON.getString("fengli");
                    String low = forecastJSON.getString("low");
                    String fengxiang = forecastJSON.getString("fengxiang");
                    String type = forecastJSON.getString("type");
                    list.add(new Weather(date, high, fengli, low, fengxiang, type));
                }
            } else {
                Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}