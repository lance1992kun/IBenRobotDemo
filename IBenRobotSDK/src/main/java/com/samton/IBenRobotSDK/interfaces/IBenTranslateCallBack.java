package com.samton.IBenRobotSDK.interfaces;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/10
 *     desc   : 翻译回调
 *     version: 1.0
 * </pre>
 */

public interface IBenTranslateCallBack {
    /**
     * 翻译结果
     *
     * @param result 返回结果
     */
    void onResult(String result);

    /**
     * 错误回调
     *
     * @param errorMsg 错误信息
     */
    void onError(String errorMsg);
}
