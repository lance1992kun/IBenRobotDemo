package com.samton.IBenRobotSDK.data;

import java.util.List;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/30
 *     desc   : 初始化机器人实体类
 *     version: 1.0
 * </pre>
 */

public final class InitBean {

    /**
     * _token_iben : null
     * set : {"leftHeadImg":"http://121.41.40.145:7080/img/deault/robot_icon.png","rightHeadImg":"http://121.41.40.145:7080/img/deault/user_icon.png","logoImg":"http://121.41.40.145:7080/img/deault/company_logo.png","questionTip":["回充电桩","宣讲模式","今天天气怎么样","打印个名片","跳个舞吧","看看我颜值多少分","讲个笑话吧","北京的景区"]}
     * broadcast : {"broadcastId":164,"name":"十八大会议","describe":"会议有什么要求","state":1,"mainImg":"http://121.41.40.145:7080/img/robotLogo/2018-01-09_f38376.png"}
     * currentMap : {"mapName":"小笨新公司","imgUrl":"http://121.41.40.145:7080/img/mapImgFile/2018-01-09_623bf7.jpg","positionPoints":[{"name":"\b办公区","location":"00","broadcast":""},{"name":"\b办公区2","location":"00","broadcast":""},{"name":"\b办公区3","location":"00","broadcast":""},{"name":"\b办公区4","location":"00","broadcast":""},{"name":"\b办公区5","location":"00","broadcast":""},{"name":"\b饮水间","location":"01","broadcast":""}],"file":"http://121.41.40.145:7080/img/mapFile/2018-01-09_623bf7"}
     * expressionMap : {"awaken":"http://121.41.40.145:7080/img/face/default/awaken.gif","anthomaniac":"http://121.41.40.145:7080/img/face/default/anthomaniac.gif","sad":"http://121.41.40.145:7080/img/face/default/sad.gif","happy":"http://121.41.40.145:7080/img/face/default/happy.gif","speak":"http://121.41.40.145:7080/img/face/default/talk.gif","smile":"http://121.41.40.145:7080/img/face/default/smile.gif"}
     * robotName : 小笨
     * robotMod : 1
     * maps : [{"mapName":"小笨总部","imgUrl":"http://121.41.40.145:7080/img/mapImgFile/2018-01-09_592cbe.jpg","file":"http://121.41.40.145:7080/img/mapFile/2018-01-09_592cbe","positionPoints":[{"name":"\b办公区","location":"00","broadcast":""},{"name":"\b饮水间","location":"01","broadcast":""}]},{"mapName":"小笨新公司","imgUrl":"http://121.41.40.145:7080/img/mapImgFile/2018-01-09_623bf7.jpg","file":"http://121.41.40.145:7080/img/mapFile/2018-01-09_623bf7","positionPoints":[{"name":"\b办公区","location":"00","broadcast":""},{"name":"\b办公区2","location":"00","broadcast":""},{"name":"\b办公区3","location":"00","broadcast":""},{"name":"\b办公区4","location":"00","broadcast":""},{"name":"\b办公区5","location":"00","broadcast":""},{"name":"\b饮水间","location":"01","broadcast":""}]}]
     * meeting : {"meetingId":90,"name":"小笨大会议","meetingDate":1515513600000,"meetingStartTime":3600000,"meetingEndTime":10800000,"newVisitorWords":"你好呀","repeatVisitorWords":"你来过了","vipVisitorWords":"vip你好","state":1}
     * preachGroup : {"groupId":74,"name":"小笨宣讲组","describe":"小笨宣讲描述","sceneId":55,"state":1,"sceneName":"小笨新公司","preachItemList":[{"itemId":372,"groupId":74,"slocId":145,"preachType":1,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_ba9a4c.jpg","content":"我已到达办公区","contentOnTheWay":"带我去办公区","showType":1,"orderNum":1,"state":1,"slocName":"\b办公区"},{"itemId":373,"groupId":74,"slocId":146,"preachType":1,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_580923.jpg","content":"我已到达办公区2","contentOnTheWay":"带我去办公区2","showType":2,"orderNum":2,"state":1,"slocName":"\b办公区2"},{"itemId":374,"groupId":74,"slocId":147,"preachType":2,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_4098a5.jpg","voiceUrl":"http://121.41.40.145:7080/img/preach/voice/2018-01-09_bafc41.mp3","contentOnTheWay":"带我去办公区3","showType":0,"orderNum":3,"state":1,"slocName":"\b办公区3"},{"itemId":375,"groupId":74,"slocId":148,"preachType":3,"videoUrl":"http://121.41.40.145:7080/img/preach/video/2018-01-09_b8fe7c.mp4","contentOnTheWay":"带我去办公区4","showType":0,"orderNum":4,"state":1,"slocName":"\b办公区4"},{"itemId":376,"groupId":74,"slocId":149,"orderNum":5,"state":1,"slocName":"\b办公区5"},{"itemId":377,"groupId":74,"slocId":150,"orderNum":6,"state":1,"slocName":"\b饮水间"}]}
     * printGroup : {"groupId":86,"name":"小笨名片","state":1,"prints":[{"printId":168,"name":"小笨名片","describe":"魂牵梦萦","state":1,"printItems":[{"printItemId":749,"content":"我想打印","style":{"type":"jsonb","value":"{\"size\": 3, \"type\": 1, \"align\": 2, \"width\": null, \"height\": null, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":1,"state":1},{"printItemId":750,"content":"http://121.41.40.145:7080/img/print//2018-01-09_f11edb.jpg","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 2, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": null}"},"printId":168,"orderNum":2,"state":1},{"printItemId":751,"content":"魂牵梦萦","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 3, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":3,"state":1}]}]}
     * workAttendance : {"workAttendanceId":69,"companyName":"慧闻科技","startTime":3600000,"endTime":5400000,"state":1}
     * voiceTag : xiaoyan
     */

    private int rs;
    private DataBean data;
    private SetBean set;
    private BroadcastBean broadcast;
    private CurrentMapBean currentMap;
    private ExpressionMapBean expressionMap;
    private String robotName;
    private int robotMod;
    private MeetingBean meeting;
    private PreachGroupBean preachGroup;
    private PrintGroupBean printGroup;
    private WorkAttendanceBean workAttendance;
    private String voiceTag;
    private List<MapsBean> maps;

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        private String errorCode;
        private String errorMsg;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public SetBean getSet() {
        return set;
    }

    public void setSet(SetBean set) {
        this.set = set;
    }

    public BroadcastBean getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(BroadcastBean broadcast) {
        this.broadcast = broadcast;
    }

    public CurrentMapBean getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(CurrentMapBean currentMap) {
        this.currentMap = currentMap;
    }

    public ExpressionMapBean getExpressionMap() {
        return expressionMap;
    }

    public void setExpressionMap(ExpressionMapBean expressionMap) {
        this.expressionMap = expressionMap;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public int getRobotMod() {
        return robotMod;
    }

    public void setRobotMod(int robotMod) {
        this.robotMod = robotMod;
    }

    public MeetingBean getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingBean meeting) {
        this.meeting = meeting;
    }

    public PreachGroupBean getPreachGroup() {
        return preachGroup;
    }

    public void setPreachGroup(PreachGroupBean preachGroup) {
        this.preachGroup = preachGroup;
    }

    public PrintGroupBean getPrintGroup() {
        return printGroup;
    }

    public void setPrintGroup(PrintGroupBean printGroup) {
        this.printGroup = printGroup;
    }

    public WorkAttendanceBean getWorkAttendance() {
        return workAttendance;
    }

    public void setWorkAttendance(WorkAttendanceBean workAttendance) {
        this.workAttendance = workAttendance;
    }

    public String getVoiceTag() {
        return voiceTag;
    }

    public void setVoiceTag(String voiceTag) {
        this.voiceTag = voiceTag;
    }

    public List<MapsBean> getMaps() {
        return maps;
    }

    public void setMaps(List<MapsBean> maps) {
        this.maps = maps;
    }

    public static class SetBean {
        /**
         * leftHeadImg : http://121.41.40.145:7080/img/deault/robot_icon.png
         * rightHeadImg : http://121.41.40.145:7080/img/deault/user_icon.png
         * logoImg : http://121.41.40.145:7080/img/deault/company_logo.png
         * questionTip : ["回充电桩","宣讲模式","今天天气怎么样","打印个名片","跳个舞吧","看看我颜值多少分","讲个笑话吧","北京的景区"]
         */

        private String leftHeadImg;
        private String rightHeadImg;
        private String logoImg;
        private List<String> questionTip;

        public String getLeftHeadImg() {
            return leftHeadImg;
        }

        public void setLeftHeadImg(String leftHeadImg) {
            this.leftHeadImg = leftHeadImg;
        }

        public String getRightHeadImg() {
            return rightHeadImg;
        }

        public void setRightHeadImg(String rightHeadImg) {
            this.rightHeadImg = rightHeadImg;
        }

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }

        public List<String> getQuestionTip() {
            return questionTip;
        }

        public void setQuestionTip(List<String> questionTip) {
            this.questionTip = questionTip;
        }
    }

    public static class BroadcastBean {
        /**
         * broadcastId : 164
         * name : 十八大会议
         * describe : 会议有什么要求
         * state : 1
         * mainImg : http://121.41.40.145:7080/img/robotLogo/2018-01-09_f38376.png
         */

        private int broadcastId;
        private String name;
        private String describe;
        private int state;
        private String mainImg;

        public int getBroadcastId() {
            return broadcastId;
        }

        public void setBroadcastId(int broadcastId) {
            this.broadcastId = broadcastId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }
    }

    public static class CurrentMapBean {
        /**
         * mapName : 小笨新公司
         * imgUrl : http://121.41.40.145:7080/img/mapImgFile/2018-01-09_623bf7.jpg
         * positionPoints : [{"name":"\b办公区","location":"00","broadcast":""},{"name":"\b办公区2","location":"00","broadcast":""},{"name":"\b办公区3","location":"00","broadcast":""},{"name":"\b办公区4","location":"00","broadcast":""},{"name":"\b办公区5","location":"00","broadcast":""},{"name":"\b饮水间","location":"01","broadcast":""}]
         * file : http://121.41.40.145:7080/img/mapFile/2018-01-09_623bf7
         */

        private String mapName;
        private String imgUrl;
        private String file;
        private List<PositionPointsBean> positionPoints;

        public String getMapName() {
            return mapName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public List<PositionPointsBean> getPositionPoints() {
            return positionPoints;
        }

        public void setPositionPoints(List<PositionPointsBean> positionPoints) {
            this.positionPoints = positionPoints;
        }

        public static class PositionPointsBean {
            /**
             * name : 办公区
             * location : 00
             * broadcast :
             */

            private String name;
            private String location;
            private String broadcast;
            private List<String> otherNames;

            public List<String> getOtherNames() {
                return otherNames;
            }

            public void setOtherNames(List<String> otherNames) {
                this.otherNames = otherNames;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getBroadcast() {
                return broadcast;
            }

            public void setBroadcast(String broadcast) {
                this.broadcast = broadcast;
            }
        }
    }

    public static class ExpressionMapBean {
        /**
         * awaken : http://121.41.40.145:7080/img/face/default/awaken.gif
         * anthomaniac : http://121.41.40.145:7080/img/face/default/anthomaniac.gif
         * sad : http://121.41.40.145:7080/img/face/default/sad.gif
         * happy : http://121.41.40.145:7080/img/face/default/happy.gif
         * speak : http://121.41.40.145:7080/img/face/default/talk.gif
         * smile : http://121.41.40.145:7080/img/face/default/smile.gif
         */

        private String awaken;
        private String anthomaniac;
        private String sad;
        private String happy;
        private String speak;
        private String smile;

        public String getAwaken() {
            return awaken;
        }

        public void setAwaken(String awaken) {
            this.awaken = awaken;
        }

        public String getAnthomaniac() {
            return anthomaniac;
        }

        public void setAnthomaniac(String anthomaniac) {
            this.anthomaniac = anthomaniac;
        }

        public String getSad() {
            return sad;
        }

        public void setSad(String sad) {
            this.sad = sad;
        }

        public String getHappy() {
            return happy;
        }

        public void setHappy(String happy) {
            this.happy = happy;
        }

        public String getSpeak() {
            return speak;
        }

        public void setSpeak(String speak) {
            this.speak = speak;
        }

        public String getSmile() {
            return smile;
        }

        public void setSmile(String smile) {
            this.smile = smile;
        }
    }

    public static class MeetingBean {
        /**
         * meetingId : 90
         * name : 小笨大会议
         * meetingDate : 1515513600000
         * meetingStartTime : 3600000
         * meetingEndTime : 10800000
         * newVisitorWords : 你好呀
         * repeatVisitorWords : 你来过了
         * vipVisitorWords : vip你好
         * state : 1
         */

        private int meetingId;
        private String name;
        private long meetingDate;
        private int meetingStartTime;
        private int meetingEndTime;
        private String newVisitorWords;
        private String repeatVisitorWords;
        private String vipVisitorWords;
        private int state;

        public int getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(int meetingId) {
            this.meetingId = meetingId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getMeetingDate() {
            return meetingDate;
        }

        public void setMeetingDate(long meetingDate) {
            this.meetingDate = meetingDate;
        }

        public int getMeetingStartTime() {
            return meetingStartTime;
        }

        public void setMeetingStartTime(int meetingStartTime) {
            this.meetingStartTime = meetingStartTime;
        }

        public int getMeetingEndTime() {
            return meetingEndTime;
        }

        public void setMeetingEndTime(int meetingEndTime) {
            this.meetingEndTime = meetingEndTime;
        }

        public String getNewVisitorWords() {
            return newVisitorWords;
        }

        public void setNewVisitorWords(String newVisitorWords) {
            this.newVisitorWords = newVisitorWords;
        }

        public String getRepeatVisitorWords() {
            return repeatVisitorWords;
        }

        public void setRepeatVisitorWords(String repeatVisitorWords) {
            this.repeatVisitorWords = repeatVisitorWords;
        }

        public String getVipVisitorWords() {
            return vipVisitorWords;
        }

        public void setVipVisitorWords(String vipVisitorWords) {
            this.vipVisitorWords = vipVisitorWords;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public static class PreachGroupBean {
        /**
         * groupId : 74
         * name : 小笨宣讲组
         * describe : 小笨宣讲描述
         * sceneId : 55
         * state : 1
         * sceneName : 小笨新公司
         * preachItemList : [{"itemId":372,"groupId":74,"slocId":145,"preachType":1,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_ba9a4c.jpg","content":"我已到达办公区","contentOnTheWay":"带我去办公区","showType":1,"orderNum":1,"state":1,"slocName":"\b办公区"},{"itemId":373,"groupId":74,"slocId":146,"preachType":1,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_580923.jpg","content":"我已到达办公区2","contentOnTheWay":"带我去办公区2","showType":2,"orderNum":2,"state":1,"slocName":"\b办公区2"},{"itemId":374,"groupId":74,"slocId":147,"preachType":2,"imgUrl":"http://121.41.40.145:7080/img/preach/img/2018-01-09_4098a5.jpg","voiceUrl":"http://121.41.40.145:7080/img/preach/voice/2018-01-09_bafc41.mp3","contentOnTheWay":"带我去办公区3","showType":0,"orderNum":3,"state":1,"slocName":"\b办公区3"},{"itemId":375,"groupId":74,"slocId":148,"preachType":3,"videoUrl":"http://121.41.40.145:7080/img/preach/video/2018-01-09_b8fe7c.mp4","contentOnTheWay":"带我去办公区4","showType":0,"orderNum":4,"state":1,"slocName":"\b办公区4"},{"itemId":376,"groupId":74,"slocId":149,"orderNum":5,"state":1,"slocName":"\b办公区5"},{"itemId":377,"groupId":74,"slocId":150,"orderNum":6,"state":1,"slocName":"\b饮水间"}]
         */

        private int groupId;
        private String name;
        private String describe;
        private int sceneId;
        private int state;
        private String sceneName;
        private List<PreachItemListBean> preachItemList;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getSceneId() {
            return sceneId;
        }

        public void setSceneId(int sceneId) {
            this.sceneId = sceneId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public List<PreachItemListBean> getPreachItemList() {
            return preachItemList;
        }

        public void setPreachItemList(List<PreachItemListBean> preachItemList) {
            this.preachItemList = preachItemList;
        }

        public static class PreachItemListBean {
            /**
             * itemId : 372
             * groupId : 74
             * slocId : 145
             * preachType : 1
             * imgUrl : http://121.41.40.145:7080/img/preach/img/2018-01-09_ba9a4c.jpg
             * content : 我已到达办公区
             * contentOnTheWay : 带我去办公区
             * showType : 1
             * orderNum : 1
             * state : 1
             * slocName : 办公区
             * voiceUrl : http://121.41.40.145:7080/img/preach/voice/2018-01-09_bafc41.mp3
             * videoUrl : http://121.41.40.145:7080/img/preach/video/2018-01-09_b8fe7c.mp4
             */

            private int itemId;
            private int groupId;
            private int slocId;
            private int preachType;
            private String imgUrl;
            private String content;
            private String contentOnTheWay;
            private int showType;
            private int orderNum;
            private int state;
            private String slocName;
            private String voiceUrl;
            private String videoUrl;
            private String location;
            private String voiceOldname;
            private String videoOldname;
            private String imgOldname;
            private boolean isReach;

            public boolean isReach() {
                return isReach;
            }

            public void setReach(boolean reach) {
                isReach = reach;
            }

            public String getVoiceOldname() {
                return voiceOldname;
            }

            public void setVoiceOldname(String voiceOldname) {
                this.voiceOldname = voiceOldname;
            }

            public String getVideoOldname() {
                return videoOldname;
            }

            public void setVideoOldname(String videoOldname) {
                this.videoOldname = videoOldname;
            }

            public String getImgOldname() {
                return imgOldname;
            }

            public void setImgOldname(String imgOldname) {
                this.imgOldname = imgOldname;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public int getSlocId() {
                return slocId;
            }

            public void setSlocId(int slocId) {
                this.slocId = slocId;
            }

            public int getPreachType() {
                return preachType;
            }

            public void setPreachType(int preachType) {
                this.preachType = preachType;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContentOnTheWay() {
                return contentOnTheWay;
            }

            public void setContentOnTheWay(String contentOnTheWay) {
                this.contentOnTheWay = contentOnTheWay;
            }

            public int getShowType() {
                return showType;
            }

            public void setShowType(int showType) {
                this.showType = showType;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getSlocName() {
                return slocName;
            }

            public void setSlocName(String slocName) {
                this.slocName = slocName;
            }

            public String getVoiceUrl() {
                return voiceUrl;
            }

            public void setVoiceUrl(String voiceUrl) {
                this.voiceUrl = voiceUrl;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }

    public static class PrintGroupBean {
        /**
         * groupId : 86
         * name : 小笨名片
         * state : 1
         * prints : [{"printId":168,"name":"小笨名片","describe":"魂牵梦萦","state":1,"printItems":[{"printItemId":749,"content":"我想打印","style":{"type":"jsonb","value":"{\"size\": 3, \"type\": 1, \"align\": 2, \"width\": null, \"height\": null, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":1,"state":1},{"printItemId":750,"content":"http://121.41.40.145:7080/img/print//2018-01-09_f11edb.jpg","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 2, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": null}"},"printId":168,"orderNum":2,"state":1},{"printItemId":751,"content":"魂牵梦萦","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 3, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":3,"state":1}]}]
         */

        private int groupId;
        private String name;
        private int state;
        private List<PrintsBean> prints;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<PrintsBean> getPrints() {
            return prints;
        }

        public void setPrints(List<PrintsBean> prints) {
            this.prints = prints;
        }

        public static class PrintsBean {
            /**
             * printId : 168
             * name : 小笨名片
             * describe : 魂牵梦萦
             * state : 1
             * printItems : [{"printItemId":749,"content":"我想打印","style":{"type":"jsonb","value":"{\"size\": 3, \"type\": 1, \"align\": 2, \"width\": null, \"height\": null, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":1,"state":1},{"printItemId":750,"content":"http://121.41.40.145:7080/img/print//2018-01-09_f11edb.jpg","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 2, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": null}"},"printId":168,"orderNum":2,"state":1},{"printItemId":751,"content":"魂牵梦萦","style":{"type":"jsonb","value":"{\"size\": null, \"type\": 3, \"align\": 2, \"width\": 400, \"height\": 0, \"weight\": null, \"afterParagraph\": 5}"},"printId":168,"orderNum":3,"state":1}]
             */

            private int printId;
            private String name;
            private String describe;
            private int state;
            private List<PrintItemsBean> printItems;

            public int getPrintId() {
                return printId;
            }

            public void setPrintId(int printId) {
                this.printId = printId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public List<PrintItemsBean> getPrintItems() {
                return printItems;
            }

            public void setPrintItems(List<PrintItemsBean> printItems) {
                this.printItems = printItems;
            }

            public static class PrintItemsBean {
                /**
                 * printItemId : 749
                 * content : 我想打印
                 * style : {"type":"jsonb","value":"{\"size\": 3, \"type\": 1, \"align\": 2, \"width\": null, \"height\": null, \"weight\": null, \"afterParagraph\": 5}"}
                 * printId : 168
                 * orderNum : 1
                 * state : 1
                 * "printType":  2,
                 * "dynamicType":  0,
                 * "startNum":  0,
                 * "endNum":  0
                 */

                private int printItemId;
                private String content;
                private StyleBean style;
                private int printId;
                private int orderNum;
                private int state;
                private int printType;
                private int dynamicType;
                private int startNum;
                private int endNum;

                public int getPrintType() {
                    return printType;
                }

                public void setPrintType(int printType) {
                    this.printType = printType;
                }

                public int getDynamicType() {
                    return dynamicType;
                }

                public void setDynamicType(int dynamicType) {
                    this.dynamicType = dynamicType;
                }

                public int getStartNum() {
                    return startNum;
                }

                public void setStartNum(int startNum) {
                    this.startNum = startNum;
                }

                public int getEndNum() {
                    return endNum;
                }

                public void setEndNum(int endNum) {
                    this.endNum = endNum;
                }

                public int getPrintItemId() {
                    return printItemId;
                }

                public void setPrintItemId(int printItemId) {
                    this.printItemId = printItemId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public StyleBean getStyle() {
                    return style;
                }

                public void setStyle(StyleBean style) {
                    this.style = style;
                }

                public int getPrintId() {
                    return printId;
                }

                public void setPrintId(int printId) {
                    this.printId = printId;
                }

                public int getOrderNum() {
                    return orderNum;
                }

                public void setOrderNum(int orderNum) {
                    this.orderNum = orderNum;
                }

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public static class StyleBean {
                    /**
                     * type : jsonb
                     * value : {"size": 3, "type": 1, "align": 2, "width": null, "height": null, "weight": null, "afterParagraph": 5}
                     */

                    private String type;
                    private String value;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }
    }

    public static class WorkAttendanceBean {
        /**
         * workAttendanceId : 69
         * companyName : 慧闻科技
         * startTime : 3600000
         * endTime : 5400000
         * state : 1
         */

        private int workAttendanceId;
        private String companyName;
        private int startTime;
        private int endTime;
        private int state;
        private int faceCount;

        public int getFaceCount() {
            return faceCount;
        }

        public void setFaceCount(int faceCount) {
            this.faceCount = faceCount;
        }

        public int getWorkAttendanceId() {
            return workAttendanceId;
        }

        public void setWorkAttendanceId(int workAttendanceId) {
            this.workAttendanceId = workAttendanceId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public static class MapsBean {
        /**
         * mapName : 小笨总部
         * imgUrl : http://121.41.40.145:7080/img/mapImgFile/2018-01-09_592cbe.jpg
         * file : http://121.41.40.145:7080/img/mapFile/2018-01-09_592cbe
         * positionPoints : [{"name":"\b办公区","location":"00","broadcast":""},{"name":"\b饮水间","location":"01","broadcast":""}]
         */

        private String mapName;
        private String imgUrl;
        private String file;
        private List<PositionPointsBeanX> positionPoints;

        public String getMapName() {
            return mapName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public List<PositionPointsBeanX> getPositionPoints() {
            return positionPoints;
        }

        public void setPositionPoints(List<PositionPointsBeanX> positionPoints) {
            this.positionPoints = positionPoints;
        }

        public static class PositionPointsBeanX {
            /**
             * name : 办公区
             * location : 00
             * broadcast :
             */

            private String name;
            private String location;
            private String broadcast;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getBroadcast() {
                return broadcast;
            }

            public void setBroadcast(String broadcast) {
                this.broadcast = broadcast;
            }
        }
    }
}
