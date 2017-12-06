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

public class MessageHelper {
    /**
     * 获取电量回写信息
     *
     * @param batteryInfo 要回写的电量信息
     * @return 电量回写信息
     */
    public static String getBatteryMessage(String batteryInfo) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "batteryMessage");
            object.put("type", "getPowerInfo");
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
     * @param content 要回写的信息
     * @return 回写信息
     */
    public static String getMapMessage(String type, String content) {
        String result;
        try {
            JSONObject object = new JSONObject();
            object.put("command", "mapMessage");
            object.put("type", type);
            object.put("content", content);
            result = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }
}
