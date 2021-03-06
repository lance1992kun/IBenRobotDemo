package com.samton.ibenvoice.interfaces;

import com.iflytek.cloud.SpeechError;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 小笨TTS回调接口
 *     version: 1.0
 * </pre>
 */

public interface IBenTTSCallBack {
    /**
     * 开始转换
     *
     * @param tag 标识
     */
    void onSpeakBegin(int tag);

    /**
     * 转换结束
     *
     * @param tag   标识
     * @param error 错误码
     */
    void onCompleted(int tag, SpeechError error);
}
