package com.samton.IBenRobotSDK.core;

import com.samton.IBenRobotSDK.interfaces.ISerialCallBack;
import com.samton.serialport.SerialUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/01/19 13:53
 *   desc    : 小笨智能SDK - 串口工具
 *   version : 1.0
 * </pre>
 */

public final class IBenSerialUtil {
    /**
     * 串口操作工具类
     */
    private SerialUtil mSerialUtil = null;
    /**
     * 唤醒工具单例
     */
    private volatile static IBenSerialUtil instance = new IBenSerialUtil();
    /**
     * 读写回显定时器
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 串口回调
     */
    private ISerialCallBack mCallBack = null;

    /**
     * 获取串口工具单例
     *
     * @return 串口工具实例
     */
    public static IBenSerialUtil getInstance() {
        return instance;
    }

    /**
     * 私有构造
     */
    private IBenSerialUtil() {
        // 打开串口
        try {
            // 设置串口号、波特率，
            mSerialUtil = new SerialUtil("/dev/ttyS0");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置回调
     *
     * @param callBack 回调
     */
    public void setCallBack(ISerialCallBack callBack) {
        mCallBack = null;
        mCallBack = callBack;
        // 清空计时器
        mCompositeDisposable.clear();
        DisposableObserver<Long> mReadObserver = getReadTimer();
        Observable.interval(0, 20, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io()).subscribe(mReadObserver);
        // 新加计时器
        mCompositeDisposable.add(mReadObserver);
    }

    /**
     * 向串口写数据
     *
     * @param msg 要写的数据
     */
    public void sendData(String msg) {
        mSerialUtil.setData(msg.getBytes());
    }

    /**
     * 向串口写数据
     *
     * @param position 位置L,R,H
     * @param angle    角度0~270
     * @param speed    速度001-700
     */
    public void sendData(String position, String angle, String speed) {
        String data = "{S" + position + angle + speed + "}";
        mSerialUtil.setData(data.getBytes());
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
                    int startIndex = result.indexOf("{");
                    int endIndex = result.indexOf("}");
                    if (startIndex != -1 && endIndex != -1) {
                        mCallBack.onReadData(result);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

}
