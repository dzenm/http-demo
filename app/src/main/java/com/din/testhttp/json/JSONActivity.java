package com.din.testhttp.json;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityShowBinding;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONActivity extends AppCompatActivity {

    private ActivityShowBinding bind;
    private JSONWeather jsonWeather;
    private JSONExpress jsonExpress;
    private String TAG = null;
    private static final String WEATHER = "http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=efb0a5f8bab5b88e696e6e3dbb77570a";
//    private static String city = "ganzhou";
//    private static final String WEATHER = "https://www.apiopen.top/weatherApi?city=" + city;

    private static final String EXPRESS = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
    private static String data = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_show);
        bind.btnOne.setText("开始JSONObject解析数据");
        jsonWeather = new JSONWeather(this, bind.text);
        jsonExpress = new JSONExpress(this, bind.text);
        bind.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  获取天气或者快递选择
                showDialog();
            }
        });

    }

    private void getHTTPConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //  获取天气数据还是快递数据的标志
                    data = TAG.equals("FIRST") ? WEATHER : EXPRESS;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(data)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    //  通过标志解析相应的数据
                    if (TAG.equals("FIRST")) {
                        jsonWeather.Weather(data);
                    } else {
                        jsonExpress.Express(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("选择需要解析的数据").
                setNegativeButton("天气信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TAG = "FIRST";
                        getHTTPConnection();
                    }
                }).
                setPositiveButton("快递信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TAG = "SECOND";
                        getHTTPConnection();
                    }
                });
        alertDialog.create().show();
    }
}