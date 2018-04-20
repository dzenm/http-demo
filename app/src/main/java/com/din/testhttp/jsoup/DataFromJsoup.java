package com.din.testhttp.jsoup;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.din.testhttp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class DataFromJsoup {


    private TextView textView;
    private ImageView imageView;
    private Activity activity;

    //  构造方法,需要在UI线程中更新数据,所以需要使用到activity参数,TextView是用来显示数据
    public DataFromJsoup(Activity activity, ImageView imageView, TextView textView) {
        this.activity = activity;
        this.imageView = imageView;
        this.textView = textView;
    }

    public void getDataFromJsoup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("http://www.umei.cc/tags/xiezhen.htm").get();
                    //  从Document对象选择一个节点
                    Elements elements = document.select("div.TypeList");
                    String title = null, picture = null;
                    title = String.valueOf(elements.select("a").select("div.TypeBigPics").text());
                    picture = String.valueOf(elements.select("a").select("img").attr("src"));
                    showData(title, picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showData(final String data, final String url) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(data);
                Glide.with(activity).load(url).listener(requestListener).into(imageView);
            }
        });
    }

    RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };
}
