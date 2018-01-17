package com.samton.IBenRobotSDK.interfaces;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 唤醒回调
 *     version: 1.0
 * </pre>
 */

public interface IWakeUpCallBack {
    /**
     * 唤醒成功
     *
     * @param angle     唤醒角度
     * @param isPassive 是否被动唤醒(用户点击音乐跳舞这种属于被动唤醒)
     */
    void onWakeUp(int angle, boolean isPassive);
}
