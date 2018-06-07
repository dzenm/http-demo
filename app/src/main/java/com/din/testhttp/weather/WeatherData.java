package com.din.testhttp.weather;

import java.util.List;

public class WeatherData {
 
    /**
     * code : 200
     * msg : 成功!
     * data : {"yesterday":{"date":"4日星期一","high":"高温 30℃","fx":"北风","low":"低温 23℃","fl":"<![CDATA[<3级]]>","type":"小雨"},"city":"赣州","aqi":null,"forecast":[{"date":"5日星期二","high":"高温 27℃","fengli":"<![CDATA[<3级]]>","low":"低温 21℃","fengxiang":"北风","type":"雷阵雨"},{"date":"6日星期三","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 21℃","fengxiang":"东北风","type":"中雨"},{"date":"7日星期四","high":"高温 27℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"东北风","type":"中雨"},{"date":"8日星期五","high":"高温 30℃","fengli":"<![CDATA[<3级]]>","low":"低温 23℃","fengxiang":"东南风","type":"雷阵雨"},{"date":"9日星期六","high":"高温 31℃","fengli":"<![CDATA[<3级]]>","low":"低温 24℃","fengxiang":"南风","type":"雷阵雨"}],"ganmao":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。","wendu":"22"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yesterday : {"date":"4日星期一","high":"高温 30℃","fx":"北风","low":"低温 23℃","fl":"<![CDATA[<3级]]>","type":"小雨"}
         * city : 赣州
         * aqi : null
         * forecast : [{"date":"5日星期二","high":"高温 27℃","fengli":"<![CDATA[<3级]]>","low":"低温 21℃","fengxiang":"北风","type":"雷阵雨"},{"date":"6日星期三","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 21℃","fengxiang":"东北风","type":"中雨"},{"date":"7日星期四","high":"高温 27℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"东北风","type":"中雨"},{"date":"8日星期五","high":"高温 30℃","fengli":"<![CDATA[<3级]]>","low":"低温 23℃","fengxiang":"东南风","type":"雷阵雨"},{"date":"9日星期六","high":"高温 31℃","fengli":"<![CDATA[<3级]]>","low":"低温 24℃","fengxiang":"南风","type":"雷阵雨"}]
         * ganmao : 天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。
         * wendu : 22
         */

        private YesterdayBean yesterday;
        private String city;
        private String aqi;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 4日星期一
             * high : 高温 30℃
             * fx : 北风
             * low : 低温 23℃
             * fl : <![CDATA[<3级]]>
             * type : 小雨
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 5日星期二
             * high : 高温 27℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 21℃
             * fengxiang : 北风
             * type : 雷阵雨
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}