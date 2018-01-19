package com.samton.IBenRobotSDK.interfaces;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/01/19 13:55
 *   desc    : 串口回调
 *   version : 1.0
 * </pre>
 */

public interface ISerialCallBack {
    /**
     * 回写数据
     * @param result 数据
     */
    void onReadData(String result);
}
