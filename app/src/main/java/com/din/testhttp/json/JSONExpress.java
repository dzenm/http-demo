package com.din.testhttp.json;

import android.app.Activity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dinzhenyan on 2018/3/31.
 */

public class JSONExpress {

    private TextView textView;
    private Activity activity;

    //  构造方法,需要在UI线程中更新数据,所以需要使用到activity参数,TextView是用来显示数据
    public JSONExpress(Activity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    public void Express(String jsonData) {
        String time = null;
        String context = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = new JSONArray(jsonObject.getString( "data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                time = result.getString("time");
                context = result.getString("context");
                showExpress(time, context);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showExpress(final String time, final String context) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append("\n\n到达时间： " + time);
                textView.append("\n到达地点： " + context);
            }
        });
    }
}