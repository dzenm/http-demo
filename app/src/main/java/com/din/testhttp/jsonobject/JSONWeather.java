package com.din.testhttp.jsonobject;

import android.app.Activity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dinzhenyan on 2018/3/30.
 */

public class JSONWeather {

    private TextView textView;
    private static final String TAG = "TAG";
    private Activity activity;

    //  构造方法,需要在UI线程中更新数据,所以需要使用到activity参数,TextView是用来显示数据
    public JSONWeather(Activity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    //  JSON解析
    public void Weather(String jsonData) {
        try {
            String temperature = null;
            String weather = null;
            String wind = null;
            String week = null;
            String city = null;
            String date_y = null;
            String dressing_advice = null;

            //  处理json数据
            JSONObject jsonObject = new JSONObject(jsonData);
            //  获取json里的第一个数据(它是一个对象,不是数组,因此获取时用JSONObject)
            String resultcode = jsonObject.getString("resultcode");
            if (resultcode.equals("200")) {
                //  获取result键
                JSONObject result = jsonObject.getJSONObject("result");
                //  获取result键的值today
                JSONObject today = result.getJSONObject("today");
                //  获取today键的全部值
                temperature = today.getString("temperature");
                weather = today.getString("weather");
                wind = today.getString("wind");
                week = today.getString("week");
                city = today.getString("city");
                date_y = today.getString("date_y");
                dressing_advice = today.getString("dressing_advice");
                showData(temperature, weather, wind, week, city, date_y, dressing_advice);

                //  获取未来一周的天气
                JSONArray jsonArray = new JSONArray(result.getString("future"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    week = json.getString("week");
                    wind = json.getString("wind");
                    weather = json.getString("weather");
                    date_y = json.getString("date");
                    showFutureWeather(week, wind, weather, date_y);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  更新UI必须放在UI线程
    private void showData(final String temperature, final String weather, final String wind, final String week, final String city, final String date_y, final String dressing_advice) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append("\n温度  : " + temperature);
                textView.append("\n天气  : " + weather);
                textView.append("\n风力  : " + wind);
                textView.append("\n星期  : " + week);
                textView.append("\n城市  : " + city);
                textView.append("\n时间  : " + date_y);
                textView.append("\n建议  : " + dressing_advice);
            }
        });
    }

    private void showFutureWeather(final String week, final String date_y, final String weather, final String wind) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append("\n\n" + week);
                textView.append("\n    " + date_y);
                textView.append("\n    " + weather);
                textView.append("\n    " + wind);

            }
        });
    }

    /**
     *  {   "resultcode":"200",
     *      "reason":"查询成功",
     *      "result":{
     *          "sk":{
     *              "temp":"23",
     *              "wind_direction":"东南风",
     *              "wind_strength":"4级",
     *              "humidity":"44%",
     *              "time":"18:07"
     *              },
     *          "today":{
     *              "temperature":"14℃~24℃",
     *              "weather":"晴转多云",
     *              "weather_id":{"fa":"00","fb":"01"},
     *              "wind":"东南风3-5级",
     *              "week":"星期六",
     *              "city":"苏州",
     *              "date_y":"2018年03月31日",
     *              "dressing_index":"较舒适",
     *              "dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
     *              "uv_index":"中等",
     *              "comfort_index":"",
     *              "wash_index":"较适宜",
     *              "travel_index":"较适宜",
     *              "exercise_index":"较适宜",
     *              "drying_index":""
     *              },
     *          "future":[
     *              {   "temperature":"14℃~24℃",
     *                  "weather":"晴转多云",
     *                  "weather_id":{"fa":"00","fb":"01"},
     *                  "wind":"东南风3-5级",
     *                  "week":"星期六",
     *                  "date":"20180331"},
     *              {   "temperature":"16℃~26℃",
     *                  "weather":"阴",
     *                  "weather_id":{"fa":"02","fb":"02"},
     *                  "wind":"东南风3-5级",
     *                  "week":"星期日",
     *                  "date":"20180401"},
     *              {   "temperature":"18℃~27℃",
     *                  "weather":"阴转多云",
     *                  "weather_id":{"fa":"02","fb":"01"},
     *                  "wind":"东南风3-5级",
     *                  "week":"星期一",
     *                  "date":"20180402"},
     *              {   "temperature":"18℃~27℃",
     *                  "weather":"多云转阴",
     *                  "weather_id":{"fa":"01","fb":"02"},
     *                  "wind":"东南风4-5级",
     *                  "week":"星期二",
     *                  "date":"20180403"},
     *              {   "temperature":"15℃~24℃",
     *                  "weather":"小雨转小雨-中雨",
     *                  "weather_id":{"fa":"07","fb":"21"},
     *                  "wind":"东南风4-5级",
     *                  "week":"星期三",
     *                  "date":"20180404"},
     *              {   "temperature":"16℃~26℃",
     *                  "weather":"阴",
     *                  "weather_id":{"fa":"02","fb":"02"},
     *                  "wind":"东南风3-5级",
     *                  "week":"星期四",
     *                  "date":"20180405"},
     *              {   "temperature":"16℃~26℃",
     *                  "weather":"阴",
     *                  "weather_id":{"fa":"02","fb":"02"},
     *                  "wind":"东南风3-5级",
     *                  "week":"星期五",
     *                  "date":"20180406"}]
     *             },
     *      "error_code":0
     *  }
     */
}
