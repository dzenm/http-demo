package com.din.testhttp.pull;

import android.app.Activity;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by dinzhenyan on 2018/3/30.
 */

public class PullToXML {

    private TextView text;
    private Activity activity;

    //  构造方法,需要在UI线程中更新数据,所以需要使用到activity参数,TextView是用来显示数据
    public PullToXML(Activity activity, TextView textView) {
        this.activity = activity;
        this.text = textView;
    }
    //  实际解析数据开始
    public void parsePull(String xmlData) {
        try {
            //  获取XMLPullParserFactory实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //  获取XmlPullparser对象
            XmlPullParser pullParser = factory.newPullParser();
            //  将返回的数据设置进去
            pullParser.setInput(new StringReader(xmlData));
            //  获取当前解析事件
            int enevtType = pullParser.getEventType();
            String TITLE = "";
            String ARTIST = "";
            String COUNTRY = "";
            String COMPANY = "";
            String PRICE = "";
            String YEAR = "";

            while (enevtType != XmlPullParser.END_DOCUMENT) {
                //  获取当前节点的名字
                String nodeName = pullParser.getName();
                switch (enevtType) {
                    case XmlPullParser.START_TAG:
                        if ("TITLE".equals(nodeName)) {
                            TITLE = pullParser.nextText();
                        } else if ("ARTIST".equals(nodeName)) {
                            ARTIST = pullParser.nextText();
                        } else if ("COUNTRY".equals(nodeName)) {
                            COUNTRY = pullParser.nextText();
                        } else if ("COMPANY".equals(nodeName)) {
                            COMPANY = pullParser.nextText();
                        } else if ("PRICE".equals(nodeName)) {
                            PRICE = pullParser.nextText();
                        } else if ("YEAR".equals(nodeName)) {
                            YEAR = pullParser.nextText();
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        //  一个节点的结束标志
                        if ("CD".equals(nodeName)) {
                            showData(TITLE, ARTIST, COUNTRY, COMPANY, PRICE, YEAR);
                        }
                    default:
                        break;
                }
                //  通过next()方法获取下一个解析事件
                enevtType = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showData(final String a, final String b, final String c, final String d, final String e, final String f) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.append("TITLE is  :  " + a + "\n");
                text.append("ARTIST is  :  " + b + "\n");
                text.append("COUNTRY is  :  " + c + "\n");
                text.append("COMPANY is  :  " + d + "\n");
                text.append("PRICE is  :   " + e + "\n");
                text.append("YEAR is  :  " + f + "\n");
                text.append("\n");
            }
        });
    }
}