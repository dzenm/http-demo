package com.din.testhttp.pull;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityShowBinding;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PullActivity extends AppCompatActivity {

    private ActivityShowBinding bind;
    private PullToXML pullToXML;
    private PullToHTML pullToHTML;
    private static final String URLXML = "http://www.w3school.com.cn/example/xmle/cd_catalog.xml";
    private static final String URLHTML = "https://bbs.csdn.net/recommend_tech_topics.atom";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_show);
        pullToXML = new PullToXML(this, bind.text);
        pullToHTML = new PullToHTML(this, bind.text);
        //  XML解析
        bind.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHTTPConnection("XML");
            }
        });
        //  HTML解析
        bind.btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHTTPConnection("HTML");
            }
        });
    }

    private void getHTTPConnection(final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //  判断是XML还是HTML解析,然后进入不同的页面解析数据
                    String temp = tag;
                    //  请求数据,并返回结果
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(temp)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    //  调用解析数据方法
                    if (temp.equals("HTML")) {
                        pullToHTML.parsePull(data);
                    } else if (temp.equals("XML")) {
                        pullToXML.parsePull(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}