

* 解析之前需要通过okhttp3与解析的网页建立连接

  ​

```
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
```

注：新建一个线程，使用okhttp3连接URL。然后进入Pull解析

---







* Pull解析XML

  ​

```
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
```



注：parsePull方法中传入一个返回的字符串数据，然后根据节点解析。最后通过一个UI线程更新页面的数据

---







* SAX解析XML



```
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
```



注：SAX解析必须继承DefaultHandler，然后重写几个方法。

```Java
public class SAXToXML extends DefaultHandler {

    private String nodeName;
    private StringBuilder name;
    private StringBuilder price;

    private TextView textView;

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void startDocument() throws SAXException {
        name = new StringBuilder();
        price = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //  记录当前节点名
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //  根据当前的节点名判断将内容添加到哪一个StringBuilder对象中
        if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("price".equals(nodeName)) {
            price.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("food".equals(localName)) {
            textView.append("\n name is  :  " + name.toString().trim());
            textView.append("\n price is  :  " + price.toString().trim());
            textView.append("\n");
            //  将StringBuilder清空
            name.setLength(0);
            price.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
```







* JSON解析json的两个例子

  ​

  * 解析天气数据和快递信息

  ```Java
  private void showDialog() {
          AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
          alertDialog.setTitle("选择需要解析的数据").
                  setNegativeButton("天气信息",new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          TAG = "FIRST";
                          getHTTPConnection();
                      }
                  }).
                  setPositiveButton("快递信息", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          TAG = "SECOND";
                          getHTTPConnection();
                      }
                  });
          alertDialog.create().show();
      }
  ```

  ​

  ---



​	1、天气信息



```
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
```



​	2、快递信息

```
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
```



---







* jsoup解析html数据

```
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
```



---

