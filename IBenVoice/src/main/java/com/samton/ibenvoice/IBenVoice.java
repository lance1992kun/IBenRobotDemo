package com.samton.ibenvoice;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.samton.ibenvoice.interfaces.IBenRecordCallBack;
import com.samton.ibenvoice.interfaces.IBenTTSCallBack;
import com.samton.ibenvoice.media.RecordManager;
import com.samton.ibenvoice.media.TTSManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <pre>
 *   @author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/05/09 10:16
 *   desc    : 小笨语音功能SDK
 *   version : 1.0
 * </pre>
 */
public class IBenVoice {
    /**
     * String构造对象
     */
    private static StringBuilder mStringBuilder = null;
    /**
     * Map对象用来保存识别数据
     */
    private HashMap<String, String> map = null;
    /**
     * 录音管理类
     */
    private RecordManager mRecordManager = null;
    /**
     * 语音合成管理类
     */
    private TTSManager mTTSManager = null;
    /**
     * 语音回调接口
     */
    private IBenRecordCallBack mRecordCallBack = null;
    /**
     * 语音合成接口
     */
    private IBenTTSCallBack mTTSCallBack = null;
    /**
     * 录音标识
     */
    private int mRecordTag = -1;
    /**
     * 语音合成标识
     */
    private int mTtsTag = -1;
    /**
     * 科大讯飞识别监听
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            // 当前正在说话 i = 音量大小
            mRecordCallBack.onVolumeChanged(mRecordTag, i, bytes);
        }

        @Override
        public void onBeginOfSpeech() {
            // 开始说话
            mRecordCallBack.onBeginOfSpeech(mRecordTag);
        }

        @Override
        public void onEndOfSpeech() {
            // 结束说话
            mRecordCallBack.onEndOfSpeech(mRecordTag);
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            doResult(recognizerResult);
            if (isLast) {
                // 最终识别结果
                String result;
                if (mStringBuilder != null) {
                    result = mStringBuilder.toString().trim();
                    if (TextUtils.isEmpty(result)) {
                        mRecordCallBack.onError(mRecordTag, "识别结果为空");
                    } else {
                        if (!"。".equals(result)) {
                            mRecordCallBack.onResult(mRecordTag, result);
                        } else {
                            mRecordCallBack.onError(mRecordTag, "识别结果为空");
                        }
                    }
                    mStringBuilder.delete(0, mStringBuilder.length());
                }

            }
        }

        @Override
        public void onError(SpeechError speechError) {
            mRecordCallBack.onError(mRecordTag, speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
        }
    };
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            // showTip("开始播放");
            mTTSCallBack.onSpeakBegin(mTtsTag);
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // 合成进度
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
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            mTTSCallBack.onCompleted(mTtsTag, error);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    /**
     * 私有构造函数
     */
    private IBenVoice() {

    }

    /**
     * 获取小笨语音服务SDK
     *
     * @return 小笨语音服务SDK
     */
    public static IBenVoice getInstance() {
        return SingleHolder.instance;
    }

    /**
     * 初始化
     *
     * @param mContext 上下文对象
     */
    public void init(Context mContext) {
        if (mRecordManager == null) {
            mRecordManager = new RecordManager(mContext);
        }
        if (mTTSManager == null) {
            mTTSManager = new TTSManager(mContext);
        }
    }

    /**
     * 设置语音回调接口
     */
    public void setRecordListener(IBenRecordCallBack mCallBack) {
        mRecordCallBack = mCallBack;
    }

    /**
     * 设置语音合成接口
     */
    public void setTTSListener(IBenTTSCallBack mCallBack) {
        mTTSCallBack = mCallBack;
    }

    /**
     * 开始合成语音
     *
     * @param msg 需要合成语音的文字
     */
    public void startSpeaking(String msg) {
        startSpeaking(-1, msg);
    }

    /**
     * 开始合成语音
     *
     * @param tag 标识
     * @param msg 需要合成语音的文字
     */
    public void startSpeaking(int tag, String msg) {
        mTtsTag = tag;
        mTTSManager.setParam();
        mTTSManager.startSpeaking(msg, mTtsListener);
    }

    /**
     * 开始识别
     */
    public void startRecognize() {
        startRecognize(-1);
    }

    /**
     * 开始识别
     *
     * @param tag 标识
     */
    public void startRecognize(int tag) {
        // 设置此次的标识
        mRecordTag = tag;
        // 清空之前的识别结果
        map = new LinkedHashMap<>();
        mRecordManager.setParam();
        mRecordManager.startListener(mRecognizerListener);
    }

    /**
     * 是否在录音状态
     *
     * @return 是否在录音状态
     */
    public boolean isListening() {
        return mRecordManager.isListening();
    }

    /**
     * 结束识别
     */
    public void stopRecognize() {
        // 结束语音识别
        mRecordManager.cancel();
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
        // 回收科大讯飞录音
        if (mRecordManager != null) {
            mRecordManager.cancel();
            mRecordManager.destroy();
            // 清空回调函数
            mRecordCallBack = null;
        }
        // 回收资源
        if (mTTSManager != null) {
            mTTSManager.stopSpeaking();
            mTTSManager.destroy();
            // 清空回调函数
            mTTSCallBack = null;
        }
    }

    /**
     * 识别技术的操作
     */
    private void doResult(RecognizerResult recognizerResult) {
        // 解析JSON
        String results = parseIatResult(recognizerResult.getResultString());
        if (TextUtils.isEmpty(results)) {
            return;
        }
        // 读取json结果中的sn字段
        String sn = null;
        try {
            JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 将数据存入map集合
        map.put(sn, results);
        // 初始化解析数据帮助类
        mStringBuilder = new StringBuilder();
        // 将数据放入帮助类中
        for (String key : map.keySet()) {
            mStringBuilder.append(map.get(key));
        }
    }

    /**
     * 解析科大讯飞识别结果
     *
     * @param json 科大讯飞的识别Json
     * @return 解析后的String
     */
    private String parseIatResult(String json) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONTokener token = new JSONTokener(json);
            JSONObject joResult = new JSONObject(token);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                sb.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 内部静态类
     */
    private static class SingleHolder {
        private static final IBenVoice instance = new IBenVoice();
    }
}
