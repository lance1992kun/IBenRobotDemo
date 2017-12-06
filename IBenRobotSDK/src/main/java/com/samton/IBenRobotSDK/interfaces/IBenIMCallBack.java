package com.samton.IBenRobotSDK.interfaces;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/10
 *     desc   : 发送消息(文件、图片、声音)回调
 *     version: 1.0
 * </pre>
 */

public interface IBenIMCallBack {
    /**
     * 成功回调
     *
     * @param resultCode 返回码
     * @param resultData 返回信息
     */
    void onSuccess(int resultCode, String resultData);

    /**
     * 错误回调
     *
     * @param errorMsg 错误信息
     */
    void onFailed(String errorMsg);
}
