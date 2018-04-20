package com.din.testhttp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.testhttp.databinding.ActivityMainBinding;
import com.din.testhttp.express.ExpressActivity;
import com.din.testhttp.gson.GSONActivity;
import com.din.testhttp.html.HTMLActivity;
import com.din.testhttp.jsonobject.JSONActivity;
import com.din.testhttp.jsoup.JsoupActivity;
import com.din.testhttp.pull.PullActivity;
import com.din.testhttp.sax.SAXActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bind;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bind.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PullActivity.class));
            }
        });

        bind.btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SAXActivity.class));
            }
        });

        bind.btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, JSONActivity.class));
            }
        });

        bind.btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GSONActivity.class));
            }
        });
        bind.btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpressActivity.class));
            }
        });

        bind.btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, JsoupActivity.class));
            }
        });

        bind.btnSever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HTMLActivity.class));
            }
        });


    }
}