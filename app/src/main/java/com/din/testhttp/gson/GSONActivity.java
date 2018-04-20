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
                            .url("http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=efb0a5f8bab5b88e696e6e3dbb77570a")
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
