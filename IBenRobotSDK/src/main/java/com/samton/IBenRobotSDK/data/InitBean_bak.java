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
@Deprecated
public final class InitBean_bak {
    /**
     * 机器人工作的模式
     */
    private String robotMod;
    /**
     * token信息
     */
    private String _token_iben;
    /**
     * 发音人
     */
    private String voiceTag;
    /**
     * 机器人名字
     */
    private String robotName;
    /**
     * 机器人当前地图对象
     */
    private MapBean currentMap;
    /**
     * 机器人地图列表对象
     */
    private List<MapBean> maps;
    /**
     * 机器人表情列表
     */
    private ExpressionListBean expressionList;

    public String getRobotMod() {
        return robotMod;
    }

    public void setRobotMod(String robotMod) {
        this.robotMod = robotMod;
    }

    public String get_token_iben() {
        return _token_iben;
    }

    public void set_token_iben(String _token_iben) {
        this._token_iben = _token_iben;
    }

    public String getVoiceTag() {
        return voiceTag;
    }

    public void setVoiceTag(String voiceTag) {
        this.voiceTag = voiceTag;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public MapBean getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(MapBean currentMap) {
        this.currentMap = currentMap;
    }

    public List<MapBean> getMaps() {
        return maps;
    }

    public void setMaps(List<MapBean> maps) {
        this.maps = maps;
    }

    public ExpressionListBean getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(ExpressionListBean expressionList) {
        this.expressionList = expressionList;
    }

    /**
     * 单个地图的对象
     */
    public class MapBean {
        /**
         * 地图名字
         */
        private String mapName;
        /**
         * 地图文件地址
         */
        private String file;
        /**
         * 播报语
         */
        private String broadcast;
        /**
         * 当前地图的点位
         */
        private List<PositionPointBean> positionPoints;

        public String getBroadcast() {
            return broadcast;
        }

        public void setBroadcast(String broadcast) {
            this.broadcast = broadcast;
        }

        public String getMapName() {
            return mapName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public List<PositionPointBean> getPositionPoints() {
            return positionPoints;
        }

        public void setPositionPoints(List<PositionPointBean> positionPoints) {
            this.positionPoints = positionPoints;
        }

        /**
         * 各种点坐标单对象
         */
        public class PositionPointBean {
            /**
             * 地点名称
             */
            private String name;
            /**
             * 地点坐标
             */
            private String location;

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
        }
    }

    /**
     * 表情对象
     */
    public class ExpressionListBean {
        /**
         * 唤醒表情
         */
        private String awaken;
        /**
         * 花痴表情
         */
        private String anthomaniac;
        /**
         * 悲伤表情
         */
        private String sad;
        /**
         * 开心表情
         */
        private String happy;
        /**
         * 说话表情
         */
        private String speak;
        /**
         * 微笑表情
         */
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
}
