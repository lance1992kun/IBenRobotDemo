package com.samton.IBenRobotSDK.data;

import java.util.List;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/05/04
 *     desc   : 小笨回答数据模型
 *     version: 1.0
 * </pre>
 */

public final class MessageBean {


    /**
     * data : {"appMessage":[{"answerType":-1,"informationJson":{"current":{"city":"北京","date":"2017-07-06","iname":"空气污染扩散指数","index":"优","temp":"25","temphigh":"25","templow":"23","weather":"大雨","week":"星期四","winddirect":"东风","windpower":"3级"},"daily":[{"date":"2017-07-06","day":{"img":"9","temphigh":"25","weather":"大雨","winddirect":"东风","windpower":"微风"},"night":{"img":"8","templow":"23","weather":"中雨","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:45","week":"星期四"},{"date":"2017-07-07","day":{"img":"2","temphigh":"31","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"23","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:44","week":"星期五"},{"date":"2017-07-08","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:55","sunset":"19:44","week":"星期六"},{"date":"2017-07-09","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"26","weather":"多云","winddirect":"南风","windpower":"微风"},"sunrise":"04:56","sunset":"19:44","week":"星期日"},{"date":"2017-07-10","day":{"img":"2","temphigh":"33","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"2","templow":"25","weather":"阴","winddirect":"北风","windpower":"微风"},"sunrise":"04:56","sunset":"19:43","week":"星期一"},{"date":"2017-07-11","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"东南风","windpower":"微风"},"night":{"img":"0","templow":"24","weather":"晴","winddirect":"东南风","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期二"},{"date":"2017-07-12","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期三"}]},"isAnswer":true,"message":"北京今天23℃~25℃ 裤。"}],"code":"10010","info":"消息发送成功","time":"2017-07-06 10:48:48"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appMessage : [{"answerType":-1,"informationJson":{"current":{"city":"北京","date":"2017-07-06","iname":"空气污染扩散指数","index":"优","temp":"25","temphigh":"25","templow":"23","weather":"大雨","week":"星期四","winddirect":"东风","windpower":"3级"},"daily":[{"date":"2017-07-06","day":{"img":"9","temphigh":"25","weather":"大雨","winddirect":"东风","windpower":"微风"},"night":{"img":"8","templow":"23","weather":"中雨","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:45","week":"星期四"},{"date":"2017-07-07","day":{"img":"2","temphigh":"31","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"23","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:44","week":"星期五"},{"date":"2017-07-08","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:55","sunset":"19:44","week":"星期六"},{"date":"2017-07-09","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"26","weather":"多云","winddirect":"南风","windpower":"微风"},"sunrise":"04:56","sunset":"19:44","week":"星期日"},{"date":"2017-07-10","day":{"img":"2","temphigh":"33","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"2","templow":"25","weather":"阴","winddirect":"北风","windpower":"微风"},"sunrise":"04:56","sunset":"19:43","week":"星期一"},{"date":"2017-07-11","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"东南风","windpower":"微风"},"night":{"img":"0","templow":"24","weather":"晴","winddirect":"东南风","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期二"},{"date":"2017-07-12","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期三"}]},"isAnswer":true,"message":"北京今天23℃~25℃ 裤。"}]
         * code : 10010
         * info : 消息发送成功
         * time : 2017-07-06 10:48:48
         */

        private String code;
        private String info;
        private String time;
        private List<AppMessageBean> appMessage;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<AppMessageBean> getAppMessage() {
            return appMessage;
        }

        public void setAppMessage(List<AppMessageBean> appMessage) {
            this.appMessage = appMessage;
        }

        public static class AppMessageBean {
            /**
             * answerType : -1
             * informationJson : {"current":{"city":"北京","date":"2017-07-06","iname":"空气污染扩散指数","index":"优","temp":"25","temphigh":"25","templow":"23","weather":"大雨","week":"星期四","winddirect":"东风","windpower":"3级"},"daily":[{"date":"2017-07-06","day":{"img":"9","temphigh":"25","weather":"大雨","winddirect":"东风","windpower":"微风"},"night":{"img":"8","templow":"23","weather":"中雨","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:45","week":"星期四"},{"date":"2017-07-07","day":{"img":"2","temphigh":"31","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"23","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:44","week":"星期五"},{"date":"2017-07-08","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:55","sunset":"19:44","week":"星期六"},{"date":"2017-07-09","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"26","weather":"多云","winddirect":"南风","windpower":"微风"},"sunrise":"04:56","sunset":"19:44","week":"星期日"},{"date":"2017-07-10","day":{"img":"2","temphigh":"33","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"2","templow":"25","weather":"阴","winddirect":"北风","windpower":"微风"},"sunrise":"04:56","sunset":"19:43","week":"星期一"},{"date":"2017-07-11","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"东南风","windpower":"微风"},"night":{"img":"0","templow":"24","weather":"晴","winddirect":"东南风","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期二"},{"date":"2017-07-12","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期三"}]}
             * isAnswer : true
             * message : 北京今天23℃~25℃ 裤。
             */

            private int answerType;
            private InformationJsonBean informationJson;
            private boolean isAnswer;
            private String message;

            public int getAnswerType() {
                return answerType;
            }

            public void setAnswerType(int answerType) {
                this.answerType = answerType;
            }

            public InformationJsonBean getInformationJson() {
                return informationJson;
            }

            public void setInformationJson(InformationJsonBean informationJson) {
                this.informationJson = informationJson;
            }

            public boolean isIsAnswer() {
                return isAnswer;
            }

            public void setIsAnswer(boolean isAnswer) {
                this.isAnswer = isAnswer;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public static class InformationJsonBean {
                /**
                 * current : {"city":"北京","date":"2017-07-06","iname":"空气污染扩散指数","index":"优","temp":"25","temphigh":"25","templow":"23","weather":"大雨","week":"星期四","winddirect":"东风","windpower":"3级"}
                 * daily : [{"date":"2017-07-06","day":{"img":"9","temphigh":"25","weather":"大雨","winddirect":"东风","windpower":"微风"},"night":{"img":"8","templow":"23","weather":"中雨","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:45","week":"星期四"},{"date":"2017-07-07","day":{"img":"2","temphigh":"31","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"23","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:54","sunset":"19:44","week":"星期五"},{"date":"2017-07-08","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"北风","windpower":"微风"},"sunrise":"04:55","sunset":"19:44","week":"星期六"},{"date":"2017-07-09","day":{"img":"1","temphigh":"34","weather":"多云","winddirect":"南风","windpower":"微风"},"night":{"img":"1","templow":"26","weather":"多云","winddirect":"南风","windpower":"微风"},"sunrise":"04:56","sunset":"19:44","week":"星期日"},{"date":"2017-07-10","day":{"img":"2","temphigh":"33","weather":"阴","winddirect":"南风","windpower":"微风"},"night":{"img":"2","templow":"25","weather":"阴","winddirect":"北风","windpower":"微风"},"sunrise":"04:56","sunset":"19:43","week":"星期一"},{"date":"2017-07-11","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"东南风","windpower":"微风"},"night":{"img":"0","templow":"24","weather":"晴","winddirect":"东南风","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期二"},{"date":"2017-07-12","day":{"img":"1","temphigh":"36","weather":"多云","winddirect":"","windpower":"微风"},"night":{"img":"1","templow":"25","weather":"多云","winddirect":"","windpower":"微风"},"sunrise":"07:30","sunset":"19:30","week":"星期三"}]
                 */

                private CurrentBean current;
                private List<DailyBean> daily;

                public CurrentBean getCurrent() {
                    return current;
                }

                public void setCurrent(CurrentBean current) {
                    this.current = current;
                }

                public List<DailyBean> getDaily() {
                    return daily;
                }

                public void setDaily(List<DailyBean> daily) {
                    this.daily = daily;
                }

                public static class CurrentBean {
                    /**
                     * city : 北京
                     * date : 2017-07-06
                     * iname : 空气污染扩散指数
                     * index : 优
                     * temp : 25
                     * temphigh : 25
                     * templow : 23
                     * weather : 大雨
                     * week : 星期四
                     * winddirect : 东风
                     * windpower : 3级
                     */

                    private String city;
                    private String date;
                    private String iname;
                    private String index;
                    private String temp;
                    private String temphigh;
                    private String templow;
                    private String weather;
                    private String week;
                    private String winddirect;
                    private String windpower;

                    public String getCity() {
                        return city;
                    }

                    public void setCity(String city) {
                        this.city = city;
                    }

                    public String getDate() {
                        return date;
                    }

                    public void setDate(String date) {
                        this.date = date;
                    }

                    public String getIname() {
                        return iname;
                    }

                    public void setIname(String iname) {
                        this.iname = iname;
                    }

                    public String getIndex() {
                        return index;
                    }

                    public void setIndex(String index) {
                        this.index = index;
                    }

                    public String getTemp() {
                        return temp;
                    }

                    public void setTemp(String temp) {
                        this.temp = temp;
                    }

                    public String getTemphigh() {
                        return temphigh;
                    }

                    public void setTemphigh(String temphigh) {
                        this.temphigh = temphigh;
                    }

                    public String getTemplow() {
                        return templow;
                    }

                    public void setTemplow(String templow) {
                        this.templow = templow;
                    }

                    public String getWeather() {
                        return weather;
                    }

                    public void setWeather(String weather) {
                        this.weather = weather;
                    }

                    public String getWeek() {
                        return week;
                    }

                    public void setWeek(String week) {
                        this.week = week;
                    }

                    public String getWinddirect() {
                        return winddirect;
                    }

                    public void setWinddirect(String winddirect) {
                        this.winddirect = winddirect;
                    }

                    public String getWindpower() {
                        return windpower;
                    }

                    public void setWindpower(String windpower) {
                        this.windpower = windpower;
                    }
                }

                public static class DailyBean {
                    /**
                     * date : 2017-07-06
                     * day : {"img":"9","temphigh":"25","weather":"大雨","winddirect":"东风","windpower":"微风"}
                     * night : {"img":"8","templow":"23","weather":"中雨","winddirect":"北风","windpower":"微风"}
                     * sunrise : 04:54
                     * sunset : 19:45
                     * week : 星期四
                     */

                    private String date;
                    private DayBean day;
                    private NightBean night;
                    private String sunrise;
                    private String sunset;
                    private String week;

                    public String getDate() {
                        return date;
                    }

                    public void setDate(String date) {
                        this.date = date;
                    }

                    public DayBean getDay() {
                        return day;
                    }

                    public void setDay(DayBean day) {
                        this.day = day;
                    }

                    public NightBean getNight() {
                        return night;
                    }

                    public void setNight(NightBean night) {
                        this.night = night;
                    }

                    public String getSunrise() {
                        return sunrise;
                    }

                    public void setSunrise(String sunrise) {
                        this.sunrise = sunrise;
                    }

                    public String getSunset() {
                        return sunset;
                    }

                    public void setSunset(String sunset) {
                        this.sunset = sunset;
                    }

                    public String getWeek() {
                        return week;
                    }

                    public void setWeek(String week) {
                        this.week = week;
                    }

                    public static class DayBean {
                        /**
                         * img : 9
                         * temphigh : 25
                         * weather : 大雨
                         * winddirect : 东风
                         * windpower : 微风
                         */

                        private String img;
                        private String temphigh;
                        private String weather;
                        private String winddirect;
                        private String windpower;

                        public String getImg() {
                            return img;
                        }

                        public void setImg(String img) {
                            this.img = img;
                        }

                        public String getTemphigh() {
                            return temphigh;
                        }

                        public void setTemphigh(String temphigh) {
                            this.temphigh = temphigh;
                        }

                        public String getWeather() {
                            return weather;
                        }

                        public void setWeather(String weather) {
                            this.weather = weather;
                        }

                        public String getWinddirect() {
                            return winddirect;
                        }

                        public void setWinddirect(String winddirect) {
                            this.winddirect = winddirect;
                        }

                        public String getWindpower() {
                            return windpower;
                        }

                        public void setWindpower(String windpower) {
                            this.windpower = windpower;
                        }
                    }

                    public static class NightBean {
                        /**
                         * img : 8
                         * templow : 23
                         * weather : 中雨
                         * winddirect : 北风
                         * windpower : 微风
                         */

                        private String img;
                        private String templow;
                        private String weather;
                        private String winddirect;
                        private String windpower;

                        public String getImg() {
                            return img;
                        }

                        public void setImg(String img) {
                            this.img = img;
                        }

                        public String getTemplow() {
                            return templow;
                        }

                        public void setTemplow(String templow) {
                            this.templow = templow;
                        }

                        public String getWeather() {
                            return weather;
                        }

                        public void setWeather(String weather) {
                            this.weather = weather;
                        }

                        public String getWinddirect() {
                            return winddirect;
                        }

                        public void setWinddirect(String winddirect) {
                            this.winddirect = winddirect;
                        }

                        public String getWindpower() {
                            return windpower;
                        }

                        public void setWindpower(String windpower) {
                            this.windpower = windpower;
                        }
                    }
                }
            }
        }
    }
}
