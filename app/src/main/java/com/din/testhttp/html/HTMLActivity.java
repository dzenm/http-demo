package com.din.testhttp.html;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityHtmlBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class HTMLActivity extends AppCompatActivity {

    private ActivityHtmlBinding bind;
    private List<Item> list;
    private PictureAdapter pictureAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_html);
        list = new ArrayList<>();
        pictureAdapter = new PictureAdapter(this, list);
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        bind.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromJsoup();
                bind.recyclerView.setAdapter(pictureAdapter);
            }
        });
    }

    public void getDataFromJsoup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //  从URL加载一个Document对象
                    for (int i = 2; i < 97; i++) {
                        String url = "http://www.99mm.me/meitui/mm_1_" + i + ".html";
                        Document document = Jsoup.connect(url).get();
                        //  从Document对象选择一个节点
                        Elements elements = document.select("ul[id=piclist]").select("li");
                        String title = null, picture = null;
                        for (int j = 0; j < elements.size(); j++) {
                            title = String.valueOf(elements.select("dt").select("a").get(j).select("img").attr("alt"));
                            picture = String.valueOf(elements.select("dt").select("a").get(j).select("img").attr("src"));
                            list.add(new Item(title, picture));
                        }
                    }


                    //  从URL加载一个Document对象
//                    for (int i = 2; i < 780; i++) {
//                        String url = "http://www.mmonly.cc/mmtp/list_9_"+i+".html";
//                        Document document = Jsoup.connect(url).get();
                    //    从Document对象选择一个节点
//                        Elements elements = document.select("div.imgwkc");
//                        String title = null, picture = null;
//                        for (int j = 0; j < elements.size(); j++) {
//                            title = String.valueOf(elements.select("a").get(j).select("img").attr("alt"));
//                            picture = String.valueOf(elements.select("a").get(j).select("img").attr("src"));
//                            list.add(new Item(title, picture));
//                        }
//                    }


//                    //  从URL加载一个Document对象
//                    Document document = Jsoup.connect("http://www.umei.cc/meinvtupian/meinvxiezhen/27.htm").get();
//                    //  从Document对象选择一个节点
//                    Elements elements = document.select("div.TypeList").select("li");
//                    String title = null, picture = null;
//                    for (int i = 0; i < elements.size(); i++) {
//                        title = String.valueOf(elements.select("a").select("div.ListTit").get(i).text());
//                        picture = String.valueOf(elements.select("a").select("img").get(i).attr("src"));
//                        list.add(new Item(title, picture));
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}