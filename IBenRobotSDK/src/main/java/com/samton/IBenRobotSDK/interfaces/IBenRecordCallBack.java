package com.samton.IBenRobotSDK.interfaces;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 语音识别回调
 *     version: 1.0
 * </pre>
 */

public interface IBenRecordCallBack {
    /** 开始说话*/
    void onBeginOfSpeech();
    /** 音量更改*/
    void onVolumeChanged(int i, byte[] bytes);
    /** 结束说话*/
    void onEndOfSpeech();
    /** 听写结果*/
    void onResult(String result);
    /** 错误回调*/
    void onError(String errorMsg);
}
