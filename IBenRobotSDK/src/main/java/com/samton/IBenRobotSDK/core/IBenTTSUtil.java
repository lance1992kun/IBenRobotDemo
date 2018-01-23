package com.samton.IBenRobotSDK.core;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.samton.IBenRobotSDK.interfaces.IBenTTSCallBack;
import com.samton.IBenRobotSDK.media.TTSManager;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 小笨文字转语音工具
 *     version: 1.0
 * </pre>
 */

public final class IBenTTSUtil {
    /**
     * 单例
     */
    private static IBenTTSUtil instance;
    /**
     * 录音管理类
     */
    private TTSManager mTTSManager = null;
    /**
     * 语音回调接口
     */
    private IBenTTSCallBack callBack = null;
    /**
     * 标识
     */
    private int mTag = -1;

    /**
     * 私有构造
     */
    private IBenTTSUtil() {
    }

    /**
     * 初始化
     */
    public void init(Context mContext) {
        if (mTTSManager == null) {
            mTTSManager = new TTSManager(mContext);
        }
    }

    /**
     * 单例模式
     */
    public static synchronized IBenTTSUtil getInstance() {
        if (instance == null) {
            synchronized (IBenTTSUtil.class) {
                if (instance == null) {
                    instance = new IBenTTSUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 开始合成语音
     *
     * @param msg 需要合成语音的文字
     */
    public void startSpeaking(String msg, IBenTTSCallBack callBack) {
        mTag = -1;
        this.callBack = callBack;
        mTTSManager.setParam();
        mTTSManager.startSpeaking(msg, mTtsListener);
    }

    /**
     * 开始合成语音
     *
     * @param tag 标识
     * @param msg 需要合成语音的文字
     */
    public void startSpeaking(int tag, String msg, IBenTTSCallBack callBack) {
        mTag = tag;
        this.callBack = callBack;
        mTTSManager.setParam();
        mTTSManager.startSpeaking(msg, mTtsListener);
    }

    /**
     * 获取播放状态
     *
     * @return 播放状态
     */
    public boolean isSpeaking() {
        boolean result = false;
        if (mTTSManager != null) {
            result = mTTSManager.isSpeaking();
        }
        return result;
    }

    /**
     * 停止合成
     */
    public void stopSpeaking() {
        if (mTTSManager != null) {
            mTTSManager.stopSpeaking();
        }
    }

    /**
     * 在界面销毁中调用
     */
    public void recycle() {
        // 重置标识
        mTag = -1;
        // 回收资源
        if (mTTSManager != null) {
            mTTSManager.stopSpeaking();
            mTTSManager.destroy();
            // 清空回调函数
            callBack = null;
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            // showTip("开始播放");
            callBack.onSpeakBegin(mTag);
        }

        @Override
        public void onSpeakPaused() {
            //  showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            // showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            callBack.onCompleted(mTag, error);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}
