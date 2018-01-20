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
    /**
     * 开始说话
     *
     * @param tag 标识
     */
    void onBeginOfSpeech(int tag);

    /**
     * 音量更改
     *
     * @param tag   标识
     * @param i     级别
     * @param bytes 数据
     */
    void onVolumeChanged(int tag, int i, byte[] bytes);

    /**
     * 结束说话
     *
     * @param tag 标识
     */
    void onEndOfSpeech(int tag);

    /**
     * 听写结果
     *
     * @param tag    标识
     * @param result 识别数据
     */
    void onResult(int tag, String result);

    /**
     * 错误回调
     *
     * @param tag      标识
     * @param errorMsg 错误信息
     */
    void onError(int tag, String errorMsg);
}
