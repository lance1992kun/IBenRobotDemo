package com.samton.IBenRobotSDK.data;

import android.os.Environment;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 常量
 *     version: 1.0
 * </pre>
 */

public final class Constants {
    /**
     * 机器人状态
     */
    public static final String ROBOT_INIT_STATE = "ROBOT_INIT_STATE";
    /**
     * 机器人聆听的语言
     */
    public static final String ROBOT_LANGUAGE = "ROBOT_LANGUAGE";
    /**
     * 科大讯飞APP_ID
     */
    public static final String APP_ID = "appid=5962f56e";
    /**
     * 有道翻译APP_ID
     */
    public static final String YOU_DAO_APP_ID = "588ccbd1040df3f4";
    /**
     * 云通讯APP_ID
     */
    public static final String YTX_APP_ID = "8aaf07085b3bb22e015b4b48a2ab08e2";
    /**
     * 云通讯APP_TOKEN
     */
    public static final String YTX_APP_TOKEN = "09cbc814cf14e358ddda7977d8879e88";
    /**
     * 云通讯账号(机器人的)
     */
    public static final String ROBOT_IM_ACCOUNT = "ROBOT_IM_ACCOUNT";
    /**
     * 机器人APP_KEY
     */
    public static final String ROBOT_APP_KEY = "ROBOT_APP_KEY";
    /**
     * 保存证书的路径
     */
    public static final String FACE_PATH = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/IBenService/FaceLicense";
    /**
     * 人脸识别库名称
     */
    public static final String MODEL_NAME = "attribute.model";
    /**
     * 机器人脑袋位置
     */
    public static final String ROBOT_HEAD_POSITION = "ROBOT_HEAD_POSITION";
    /**
     * 地图图片预览位置
     */
    public static final String MAP_PATH_THUMB = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/IBenService/Maps/thumb";
    /**
     * 地图保存位置
     */
    public static final String MAP_PATH = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/IBenService/Maps";
}
