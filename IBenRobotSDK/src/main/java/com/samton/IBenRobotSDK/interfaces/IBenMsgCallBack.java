package com.samton.IBenRobotSDK.interfaces;


import com.samton.IBenRobotSDK.data.MessageBean;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 与小笨聊天的回调
 *     version: 1.0
 * </pre>
 */

public interface IBenMsgCallBack {
    /**
     * 成功回调
     *
     * @param tag 标识
     * @param bean 回调帮助类
     */
    void onSuccess(int tag, MessageBean bean);

    /**
     * 失败回调
     *
     * @param tag     标识
     * @param errorMsg 错误信息
     */
    void onFailed(int tag, String errorMsg);

    /**
     * 回答状态
     *
     * @param tag 标识
     * @param isQA true为QA false为人工
     */
    void onStateChange(int tag, boolean isQA);
}
