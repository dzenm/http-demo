package com.din.testhttp.gson;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityShowBinding;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GSONActivity extends AppCompatActivity {

    private ActivityShowBinding bind;
    private GSONObjects gsonObjects;
    private String city;
    private final String URL = "https://api.seniverse.com/v3/weather/now.json?key=zbzp8471lvyfrz1e&location=" + city + "&language=zh-Hans&unit=c";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_show);
        bind.btnOne.setText("开始GSONObject解析数据");
        gsonObjects = new GSONObjects();
        gsonObjects.setTextView(bind.text);
        bind.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHTTPConnection();
            }
        });
    }

    private void getHTTPConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    gsonObjects.GSON(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
