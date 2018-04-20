package com.din.testhttp.jsoup;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityJsoupBinding;

public class JsoupActivity extends AppCompatActivity {

    private ActivityJsoupBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_jsoup);
        final DataFromJsoup jsoup = new DataFromJsoup(this, bind.image, bind.showData);
        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                jsoup.getDataFromJsoup();
                showImage();
            }
        });
    }

    private void showImage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(JsoupActivity.this).load("http://img.99mm.net/small/2018/2777.jpg").into(bind.image);
            }
        });
    }
}