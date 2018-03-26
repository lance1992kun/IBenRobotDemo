package com.samton.IBenRobotSDK.media;

import android.content.Context;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.SPUtils;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 科大讯飞文字转语音帮助类
 *     version: 1.0
 * </pre>
 */

public final class TTSManager {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    /**
     * 语音合成对象
     */
    private SpeechSynthesizer mTts;

    /**
     * 构造函数
     *
     * @param mContext 上下文对象
     */
    public TTSManager(Context mContext) {
        // 初始化上下文对象
        this.mContext = mContext;
        // 初始化
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    LogUtils.d(">>>>>>>>语音合成初始化失败");
                } else {
                    LogUtils.d(">>>>>>>>语音合成初始化成功");
                }
            }
        });
    }

    /**
     * 设置语音合成参数
     */
    public void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        // 设置在线合成发音人
        if (mTts.getParameter(SpeechConstant.ENGINE_TYPE).equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.VOICE_NAME, "vinn");
        }
        // 设置离线发音人
        else {
            // 默认发音人为小燕
            String voiceName = SPUtils.getInstance().getString("voiceSpeaker", "xiaoyan");
            // 设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voiceName);
            // 设置发音人资源路径
            mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(voiceName));
        }
        // 设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        // 保存路径
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/IBenService/Voices/" + SpeechConstant.VOICE_NAME + ".wav");
    }


    /**
     * 获取发音人资源路径
     */
    private String getResourcePath(String voiceName) {
        String tempBuffer = "";
        // 合成通用资源
        tempBuffer += ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet");
        tempBuffer += ";";
        // 发音人资源
        tempBuffer += ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/" + voiceName + ".jet");
        return tempBuffer;
    }

    /**
     * 开始转换
     *
     * @param msg      要转换的文字
     * @param listener 回调
     */
    public void startSpeaking(String msg, SynthesizerListener listener) {
        mTts.startSpeaking(msg, listener);
    }

    /**
     * 停止说话
     */
    public void stopSpeaking() {
        mTts.stopSpeaking();
    }

    /**
     * 释放链接
     */
    public void destroy() {
        mTts.destroy();
    }

    /**
     * 是否在合成状态，包括是否在播放状态，音频从服务端获取完成后，若未播放 完成，依然处于当前会话的合成中。
     */
    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }
}
