package com.din.testhttp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.testhttp.databinding.ActivityMainBinding;
import com.din.testhttp.express.ExpressActivity;
import com.din.testhttp.gson.GSONActivity;
import com.din.testhttp.html_picture.HTMLActivity;
import com.din.testhttp.json.JSONActivity;
import com.din.testhttp.jsoup.JsoupActivity;
import com.din.testhttp.kotlin.JSONKotlinActivity;
import com.din.testhttp.pull.PullActivity;
import com.din.testhttp.rxjavahtml.RxJavaActivity;
import com.din.testhttp.sax.SAXActivity;
import com.din.testhttp.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding bind;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bind.btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                startActivity(new Intent(MainActivity.this, PullActivity.class));
                break;
            case R.id.btnTwo:
                startActivity(new Intent(MainActivity.this, SAXActivity.class));
                break;
            case R.id.btnThree:
                startActivity(new Intent(MainActivity.this, JSONActivity.class));
                break;
            case R.id.btnFour:
                startActivity(new Intent(MainActivity.this, GSONActivity.class));
                break;
            case R.id.btnFive:
                startActivity(new Intent(MainActivity.this, ExpressActivity.class));
                break;
            case R.id.btnSix:
                startActivity(new Intent(MainActivity.this, JsoupActivity.class));
                break;
            case R.id.btnSever:
                startActivity(new Intent(MainActivity.this, HTMLActivity.class));
                break;
            case R.id.btnEight:
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                break;
            case R.id.btnNine:
                startActivity(new Intent(MainActivity.this, JSONKotlinActivity.class));
                break;
            case R.id.btnTen:
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                break;
            case R.id.btnEleven:
                break;
            default:
                break;
        }
    }
}