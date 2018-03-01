package com.samton.IBenRobotSDK.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2017/12/06 22:31
 *   desc    : 机器人回调信息帮助类
 *   version :1.0
 * </pre>
 */
@SuppressWarnings("unused")
public final class MessageHelper {
    /**
     * 失败码
     */
    public final static int CODE_FAILED = 0;
    /**
     * 成功码
     */
    public final static int CODE_SUCCESS = 1;
    /**
     * 类型 - 获取电源信息
     */
    public final static String TYPE_GET_POWER_INFO = "getPowerInfo";
    /**
     * 类型 - 是否在无线充电桩
     */
    public final static String TYPE_IS_HOME = "isHome";

    /**
     * 获取连接成功回写信息
     *
     * @param code         类型>>>0失败，1>>>成功
     * @param mode         模式
     * @param isRecordOpen 录音开关是否开启
     * @return 连接成功回写信息
     */
    public static String getConnectMessage(int code, int mode, boolean isRecordOpen) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "connectMessage");
            object.put("type", "connect");
            object.put("code", code);
            if (code == CODE_SUCCESS) {
                object.put("content", "连接成功" + "#" + mode + "#" + isRecordOpen);
            } else {
                object.put("content", "连接失败，当前已有客户端连接");
            }
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 获取电量回写信息
     *
     * @param code        类型>>>0失败，1>>>成功
     * @param type        获取类型>>>TYPE_GET_POWER_INFO和TYPE_IS_HOME
     * @param batteryInfo 要回写的电量信息
     * @return 电量回写信息
     */
    public static String getBatteryMessage(int code, String type, String batteryInfo) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "batteryMessage");
            object.put("type", type);
            object.put("code", code);
            object.put("content", batteryInfo);
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 获取地图类型的回写信息
     *
     * @param type    消息类型
     * @param code    类型>>>0失败，1>>>成功
     * @param content 要回写的信息
     * @return 回写信息
     */
    public static String getMapMessage(int code, String type, String content) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "mapMessage");
            object.put("type", type);
            object.put("code", code);
            object.put("content", content);
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 获取切换模式类型的回写信息
     *
     * @param code    类型>>>0失败，1>>>成功
     * @param content 要回写的信息
     * @return 回写信息
     */
    public static String getModeMessage(int code, String content) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "modeMessage");
            object.put("type", "mode");
            object.put("code", code);
            object.put("content", content);
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 获取宣讲回写信息
     *
     * @param type    消息类型
     * @param code    类型>>>0失败，1>>>成功
     * @param content 要回写的信息
     * @return 回写信息
     */
    public static String getPreachMessage(int code, String type, String content) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "preachMessage");
            object.put("type", type);
            object.put("code", code);
            object.put("content", content);
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }
}
