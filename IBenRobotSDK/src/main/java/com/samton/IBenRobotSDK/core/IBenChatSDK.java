package com.samton.IBenRobotSDK.core;

import android.content.Context;

import com.samton.IBenRobotSDK.data.ChatFlagBean;
import com.samton.IBenRobotSDK.data.MessageBean;
import com.samton.IBenRobotSDK.interfaces.IBenMsgCallBack;
import com.samton.IBenRobotSDK.net.HttpRequest;
import com.samton.IBenRobotSDK.net.HttpUtil;
import com.samton.IBenRobotSDK.utils.SPUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.samton.IBenRobotSDK.data.Constants.ROBOT_APP_KEY;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 聊天SDK
 *     version: 1.0
 * </pre>
 */

public final class IBenChatSDK {
    /**
     * 单例模式
     */
    private static IBenChatSDK instance = null;
    /**
     * 信息回调
     */
    private IBenMsgCallBack callBack = null;
    /**
     * 标识
     */
    private int mTag = -1;

    /**
     * 获取聊天SDK单例
     *
     * @return 聊天SDK单例
     */
    public static IBenChatSDK getInstance() {
        if (instance == null) {
            synchronized (IBenChatSDK.class) {
                if (instance == null) {
                    instance = new IBenChatSDK();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造函数
     */
    private IBenChatSDK() {
    }

    /**
     * 初始化SDK
     *
     * @param callBack 回调函数
     */
    public void initSDK(IBenMsgCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 初始化IM模块
     *
     * @param mContext 上下文对象
     */
    public void initIMSDK(Context mContext) {
        // 初始化IM模块
        IBenIMHelper.getInstance().init(mTag, mContext, callBack);
    }

    /**
     * 与小笨机器人聊天
     *
     * @param msg 要发送的信息
     */
    public void sendMessage(final String msg) {
        // 标识位赋值
        mTag = -1;
        HttpUtil.getInstance().create(HttpRequest.class)
                .getRobotChatFlag(SPUtils.getInstance().getString(ROBOT_APP_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatFlagBean>() {
                    @Override
                    public void accept(@NonNull ChatFlagBean chatFlagBean) throws Exception {
                        // 发送信息给人工客服客服
                        if (chatFlagBean.getRs() == 1) {
                            send2IM(chatFlagBean.getAccout(), msg);
                        }
                        // 发送消息给小笨
                        else {
                            send2IBen(msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callBack.onFailed(mTag, throwable.getMessage());
                    }
                });
    }

    /**
     * 与小笨机器人聊天
     *
     * @param tag 标识
     * @param msg 要发送的信息
     */
    public void sendMessage(final int tag, final String msg) {
        // 标识位赋值
        mTag = tag;
        HttpUtil.getInstance().create(HttpRequest.class)
                .getRobotChatFlag(SPUtils.getInstance().getString(ROBOT_APP_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatFlagBean>() {
                    @Override
                    public void accept(@NonNull ChatFlagBean chatFlagBean) throws Exception {
                        // 发送信息给人工客服客服
                        if (chatFlagBean.getRs() == 1) {
                            send2IM(chatFlagBean.getAccout(), msg);
                        }
                        // 发送消息给小笨
                        else {
                            send2IBen(msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callBack.onFailed(mTag, throwable.getMessage());
                    }
                });
    }

    /**
     * 发送信息给人工客服客服
     *
     * @param account 人工客服账号
     * @param msg     要发送的消息
     */
    private void send2IM(String account, String msg) {
        // 回调状态QA为false
        callBack.onStateChange(mTag, false);
        // 发送给人工的消息
        IBenIMHelper.getInstance().sendTxtMsg(account, msg);
    }

    /**
     * 发送消息给小笨
     *
     * @param msg 要发送的消息
     */
    private void send2IBen(String msg) {
        // 回调状态QA为true
        callBack.onStateChange(mTag, true);
        // APP_KEY
        String appKey = SPUtils.getInstance().getString(ROBOT_APP_KEY);
        // 当前时间
        String time = System.currentTimeMillis() + "";
        HttpUtil.getInstance().create(HttpRequest.class)
                .send2IBen(appKey, time, msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MessageBean>() {
                    @Override
                    public void accept(@NonNull MessageBean messageBean) throws Exception {
                        // 成功回调
                        callBack.onSuccess(mTag, messageBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        // 失败回调
                        callBack.onFailed(mTag, throwable.getMessage());
                    }
                });
    }
}
