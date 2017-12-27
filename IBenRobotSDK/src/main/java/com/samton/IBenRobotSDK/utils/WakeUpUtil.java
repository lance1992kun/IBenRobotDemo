package com.samton.IBenRobotSDK.utils;

import com.samton.IBenRobotSDK.interfaces.IWakeUpCallBack;

import java.util.concurrent.TimeUnit;

import android_serialport_api.SerialUtil;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
    private Disposable mDisposable = null;
//    /**
//     * 读取线程
//     */
//     private ReadThread mReadThread = null;
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
            mSerialUtil = new SerialUtil("/dev/ttyS3", 115200, 0);
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
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mDisposable = Observable.interval(20, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        // 读取数据
                        byte[] data = mSerialUtil.getDataByte();
                        // 不为空的话进行回写
                        if (data != null) {
                            String result = new String(data);
                            int index = result.indexOf("angle");
                            if (index > 0) {
                                result = result.substring(index + 6, index + 9);
                                callBack.onWakeUp(result, false);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
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
    }
}
