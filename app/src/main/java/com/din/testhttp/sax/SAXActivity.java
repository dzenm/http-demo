package com.din.testhttp.sax;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.din.testhttp.R;
import com.din.testhttp.databinding.ActivityShowBinding;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SAXActivity extends AppCompatActivity {

    private ActivityShowBinding bind;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_show);
        bind.btnOne.setText("开始SAX解析数据");
        bind.btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHTTPConnection();
            }
        });
    }

    //  通过OkHttp建立与解析xml网页的连接
    private void getHTTPConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://www.w3school.com.cn/example/xmle/simple.xml")
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    SAXToXMLs(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //  解析xml
    private void SAXToXMLs(final String xmlData) {
        //  因为是通过TextView显示出来,所以存在更新UI,需要放在UI线程里执行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //  新建一个SAXParseFactory的实例
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                    SAXToXML saxToXML = new SAXToXML();
                    //  传参
                    saxToXML.setTextView(bind.text);
                    xmlReader.setContentHandler(saxToXML);
                    //  开始解析
                    xmlReader.parse(new InputSource(new StringReader(xmlData)));
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
