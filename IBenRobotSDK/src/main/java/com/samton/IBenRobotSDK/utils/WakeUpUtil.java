package com.samton.IBenRobotSDK.utils;

import android.text.TextUtils;

import com.samton.IBenRobotSDK.interfaces.IWakeUpCallBack;
import com.samton.serialport.SerialUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 唤醒工具(更改为6麦唤醒)
 *     version: 1.0
 * </pre>
 */

public class WakeUpUtil {
    /**
     * 串口操作工具类
     */
    private SerialUtil mSerialUtil = null;
    /**
     * 唤醒工具单例
     */
    private static WakeUpUtil instance = new WakeUpUtil();
    /**
     * 读写回显定时器
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 唤醒回调
     */
    private IWakeUpCallBack callBack = null;

    /**
     * 获取唤醒工具单例
     *
     * @return 唤醒工具实例
     */
    public static WakeUpUtil getInstance() {
        return instance;
    }

    /**
     * 私有构造
     */
    private WakeUpUtil() {
        // 打开串口
        try {
            // 设置串口号、波特率，
            mSerialUtil = new SerialUtil("/dev/ttyS3");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化唤醒工具类
     *
     * @param callBack 回调接口
     */
    public void setCallBack(@NonNull final IWakeUpCallBack callBack) {
        this.callBack = callBack;
        // 清空计时器
        mCompositeDisposable.clear();
        DisposableObserver<Long> mReadObserver = getReadTimer();
        Observable.interval(0, 20, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io()).subscribe(mReadObserver);
        // 新加计时器
        mCompositeDisposable.add(mReadObserver);
    }

    /**
     * 加强第一个麦克风(冲向人的)
     */
    public void setBeam() {
        String beamIndex = "BEAM0\n";
        mSerialUtil.setData(beamIndex.getBytes());
    }

    /**
     * 手动唤醒
     *
     * @param isPassive 是否被动唤醒(用户点击音乐跳舞这种属于被动唤醒)
     */
    public void startWakeUp(boolean isPassive) {
        // 手动开启唤醒功能并默认角度为0的麦克风进行增强录音
        mSerialUtil.setData("BEAM0\r".getBytes());
        if (callBack != null) {
            callBack.onWakeUp("0", isPassive);
        }
    }

    /**
     * 停止语音唤醒监听
     */
    public void stopWakeUp() {
        // 手动开启唤醒功能并默认角度为0的麦克风进行增强录音
        mSerialUtil.setData("RESET\r".getBytes());
        // 置空回调
        callBack = null;
        // 清空回显定时器
        // mCompositeDisposable.clear();
    }

    /**
     * 创建定时回写线程
     */
    private DisposableObserver<Long> getReadTimer() {
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                // 读取数据
                byte[] data = mSerialUtil.getDataByte();
                // 不为空的话进行回写
                if (data != null) {
                    String result = new String(data);
                    // LogUtils.e(result);
                    int index = result.indexOf("angle");
                    if (index > 0) {
                        result = result.substring(index + 6, index + 9).trim();
                        if (callBack != null) {
                            // 防止科大讯飞返回空字符串默认给角度值为0
                            if (TextUtils.isEmpty(result)) {
                                result = "0";
                            }
                            callBack.onWakeUp(result, false);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete");
            }
        };
    }
}
