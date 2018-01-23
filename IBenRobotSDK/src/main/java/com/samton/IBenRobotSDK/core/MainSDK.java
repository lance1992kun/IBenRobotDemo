package com.samton.IBenRobotSDK.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechUtility;
import com.samton.IBenRobotSDK.data.ActiveBean;
import com.samton.IBenRobotSDK.net.HttpRequest;
import com.samton.IBenRobotSDK.net.HttpUtil;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.SPUtils;
import com.samton.IBenRobotSDK.utils.Utils;
import com.youdao.sdk.app.YouDaoApplication;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.samton.IBenRobotSDK.data.Constants.APP_ID;
import static com.samton.IBenRobotSDK.data.Constants.ROBOT_APP_KEY;
import static com.samton.IBenRobotSDK.data.Constants.ROBOT_IM_ACCOUNT;
import static com.samton.IBenRobotSDK.data.Constants.YOU_DAO_APP_ID;


/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 机器人SDK入口
 *     version: 1.1
 * </pre>
 */

public final class MainSDK {
    /**
     * 初始化机器人SDK
     *
     * @param app Application对象
     */
    private MainSDK(Application app) {
        // 初始化工具类
        Utils.init(app);
        // 科大讯飞的语音系统
        SpeechUtility.createUtility(app, APP_ID);
        // 读取XML中的必须配置
        readMetaDataFromApplication(app);
        // 初始化有道在线翻译功能
        YouDaoApplication.init(app, YOU_DAO_APP_ID);
    }

    /**
     * 初始化SDK
     *
     * @param app Application对象
     *            请在XML中配置好robUuid、appKey和appId
     * @return SDK对象
     */
    public static MainSDK init(Application app) {
        return new MainSDK(app);
    }

    /**
     * 读取application 节点  meta-data 信息
     */
    private void readMetaDataFromApplication(Context mContext) {
        try {
            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            String appKey = appInfo.metaData.getString("IBEN_APPKEY");
            // 初始化机器人唯一标识
            SPUtils.getInstance().put(ROBOT_APP_KEY, TextUtils.isEmpty(appKey) ? "" : appKey);
            // 登录荣联云通讯的唯一ID
            if (TextUtils.isEmpty(SPUtils.getInstance().getString(ROBOT_IM_ACCOUNT, ""))) {
                SPUtils.getInstance().put(ROBOT_IM_ACCOUNT, TextUtils.isEmpty(appKey) ? "" : appKey);
            }
        } catch (Throwable e) {
            LogUtils.e("请在AndroidManifest中配置相应的数据");
        }
    }


    /**
     * 激活机器人
     */
    public void activeRobot(final IActiveCallBack callBack) {
        // 机器人执行激活操作
        HttpUtil.getInstance().create(HttpRequest.class)
                .activeRobot(SPUtils.getInstance().getString(ROBOT_APP_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ActiveBean>() {
                    @Override
                    public void accept(@NonNull ActiveBean initBean) throws Exception {
                        // 初始化成功
                        if (initBean.getRs() != -1) {
                            // 成功回调
                            callBack.onSuccess();
                        } else {
                            // 失败回调
                            callBack.onFailed(initBean.getData().getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        // 初始化失败
                        callBack.onFailed(throwable.getMessage());
                    }
                });
    }

    /**
     * 机器人激活回调
     */
    public interface IActiveCallBack {
        /**
         * 激活机器人成功
         */
        void onSuccess();

        /**
         * 激活机器人失败
         *
         * @param errorMsg 错误信息
         */
        void onFailed(String errorMsg);
    }
}
