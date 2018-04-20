package com.din.testhttp.pull;

import android.app.Activity;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class PullToHTML {
    private TextView text;
    private Activity activity;

    //  构造方法,需要在UI线程中更新数据,所以需要使用到activity参数,TextView是用来显示数据
    public PullToHTML(Activity activity, TextView textView) {
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
            String id = "";
            String published = "";
            String updated = "";
            String title = "";
            String summary = "";
            String author = "";

            while (enevtType != XmlPullParser.END_DOCUMENT) {
                //  获取当前节点的名字
                String nodeName = pullParser.getName();
                switch (enevtType) {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = pullParser.nextText();
                        } else if ("published".equals(nodeName)) {
                            published = pullParser.nextText();
                        } else if ("updated".equals(nodeName)) {
                            updated = pullParser.nextText();
                        } else if ("title".equals(nodeName)) {
                            title = pullParser.nextText();
                        } else if ("summary".equals(nodeName)) {
                            summary = pullParser.nextText();
                        } else if ("author".equals(nodeName)) {
                            author = pullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("entry".equals(nodeName)) {
                            //  一个节点的结束标志
                            showData(id, published, title, author, summary, updated);
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
                text.append("网址：" + a + "\n");
                text.append("   发帖时间：" + b + "\n");
                text.append(c + "\n");
                text.append("   作者 :  " + d + "\n");
                text.append(e + "\n");
                text.append("   最后更新：" + f + "\n\n");
            }
        });
    }

}