package com.din.testhttp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class OkHttpUtil {

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void sendOkHttp(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    byte[] bytes = response.body().bytes();
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
