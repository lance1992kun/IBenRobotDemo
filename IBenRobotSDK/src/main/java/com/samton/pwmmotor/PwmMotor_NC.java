package com.samton.pwmmotor;

import com.samton.IBenRobotSDK.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     @author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/07
 *     desc   : 舵机管理器(样机1)
 *     version: 2.0
 * </pre>
 */

public class PwmMotor_NC {
    /**
     * 周期
     */
    private final static int PERIOD_NS = 20 * 1000000;
    /**
     * 左手垂直时间点
     */
    private final static double LEFT_ARM_VERTICAL = 1.48 * 1000000;
    /**
     * 左手水平时间点
     */
    private final static double LEFT_ARM_HORIZONTAL = 2.08 * 1000000;
    /**
     * 右手垂直时间点
     */
    private final static double RIGHT_ARM_VERTICAL = 2.04 * 1000000;
    /**
     * 右手水平时间点
     */
    private final static double RIGHT_ARM_HORIZONTAL = 1.46 * 1000000;
    /**
     * 头部左侧时间点
     */
    private final static double HEAD_LEFT = 1.2 * 1000000;
    /**
     * 头部右侧时间点
     */
    private final static double HEAD_RIGHT = 2.0 * 1000000;
    /**
     * 头部中间时间点
     */
    private final static double HEAD_MIDDLE = 1.6 * 1000000;
    /**
     * 舵机管理器单例对象
     */
    private static PwmMotor_NC instance = new PwmMotor_NC();
    /**
     * 间隔时间(纳秒)
     */
    private final static int mPeriodNs = 20000;
    /**
     * 间隔时间(毫秒)
     */
    private final static int mPeriodMs = 16;
    /**
     * 左手缓冲值
     */
    private int tempLeftArm = (int) LEFT_ARM_VERTICAL;
    /**
     * 右手缓冲值
     */
    private int tempRightArm = (int) RIGHT_ARM_VERTICAL;
    /**
     * 头部缓冲值
     */
    private int tempHead = (int) HEAD_MIDDLE;
    /**
     * 头部计时器
     */
    private Disposable mHeadDisposable;
    /**
     * 左手计时器
     */
    private Disposable mLeftDisposable;
    /**
     * 右手计时器
     */
    private Disposable mRightDisposable;

    /**
     * 获取舵机管理器单例
     *
     * @return 舵机管理器单例
     */
    public static PwmMotor_NC getInstance() {
        if (instance == null) {
            synchronized (PwmMotor_NC.class) {
                if (instance == null) {
                    instance = new PwmMotor_NC();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造
     */
    private PwmMotor_NC() {
    }

    /**
     * 打开设备
     */
    public boolean openDevices() {
        return open();
    }

//    /**
//     * 关闭设备
//     */
//    public void closeDevices() {
//        close();
//    }

    /**
     * 左胳膊上抬
     */
    public void leftArmUp() {
        // 调整3号舵机
        // config(3, (int) LEFT_ARM_HORIZONTAL, PERIOD_NS);
        if (mLeftDisposable != null && !mLeftDisposable.isDisposed()) {
            mLeftDisposable.dispose();
        }
        mLeftDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempLeftArm > LEFT_ARM_HORIZONTAL) {
                            tempLeftArm -= mPeriodNs;
                        } else {
                            tempLeftArm += mPeriodNs;
                        }
                        if (tempLeftArm == LEFT_ARM_HORIZONTAL) {
                            mLeftDisposable.dispose();
                        }
                        config(3, tempLeftArm, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 左胳膊下降
     */
    public void leftArmDown() {
        // 调整3号舵机
        // config(3, (int) LEFT_ARM_VERTICAL, PERIOD_NS);
        if (mLeftDisposable != null && !mLeftDisposable.isDisposed()) {
            mLeftDisposable.dispose();
        }
        mLeftDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempLeftArm > LEFT_ARM_VERTICAL) {
                            tempLeftArm -= mPeriodNs;
                        } else {
                            tempLeftArm += mPeriodNs;
                        }
                        if (tempLeftArm == LEFT_ARM_VERTICAL) {
                            mLeftDisposable.dispose();
                        }
                        config(3, tempLeftArm, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 右胳膊上抬
     */
    public void rightArmUp() {
        // 调整2号舵机
        // config(2, (int) RIGHT_ARM_HORIZONTAL, PERIOD_NS);
        if (mRightDisposable != null && !mRightDisposable.isDisposed()) {
            mRightDisposable.dispose();
        }
        mRightDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempRightArm > RIGHT_ARM_HORIZONTAL) {
                            tempRightArm -= mPeriodNs;
                        } else {
                            tempRightArm += mPeriodNs;
                        }
                        if (tempRightArm == RIGHT_ARM_HORIZONTAL) {
                            mRightDisposable.dispose();
                        }
                        config(2, tempRightArm, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 右胳膊下降
     */
    public void rightArmDown() {
        // 调整2号舵机
        // config(2, (int) RIGHT_ARM_VERTICAL, PERIOD_NS);
        if (mRightDisposable != null && !mRightDisposable.isDisposed()) {
            mRightDisposable.dispose();
        }
        mRightDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempRightArm > RIGHT_ARM_VERTICAL) {
                            tempRightArm -= mPeriodNs;
                        } else {
                            tempRightArm += mPeriodNs;
                        }
                        if (tempRightArm == RIGHT_ARM_VERTICAL) {
                            mRightDisposable.dispose();
                        }
                        config(2, tempRightArm, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 头部转向左侧
     */
    public void head2Left() {
        // 调整0号舵机
//        config(0, (int) HEAD_LEFT, PERIOD_NS);
        if (mHeadDisposable != null && !mHeadDisposable.isDisposed()) {
            mHeadDisposable.dispose();
        }
        mHeadDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempHead > HEAD_LEFT) {
                            tempHead -= mPeriodNs;
                        } else {
                            tempHead += mPeriodNs;
                        }
                        if (tempHead == HEAD_LEFT) {
                            mHeadDisposable.dispose();
                        }
                        config(0, tempHead, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 头部转向右侧
     */
    public void head2Right() {
        // 调整0号舵机
//        config(0, (int) HEAD_RIGHT, PERIOD_NS);
        if (mHeadDisposable != null && !mHeadDisposable.isDisposed()) {
            mHeadDisposable.dispose();
        }
        mHeadDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempHead > HEAD_RIGHT) {
                            tempHead -= mPeriodNs;
                        } else {
                            tempHead += mPeriodNs;
                        }
                        if (tempHead == HEAD_RIGHT) {
                            mHeadDisposable.dispose();
                        }
                        config(0, tempHead, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    /**
     * 头部转向中间
     */
    public void head2Middle() {
        // 调整0号舵机
//        config(0, (int) HEAD_MIDDLE, PERIOD_NS);
        if (mHeadDisposable != null && !mHeadDisposable.isDisposed()) {
            mHeadDisposable.dispose();
        }
        mHeadDisposable = Observable.interval(mPeriodMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (tempHead > HEAD_MIDDLE) {
                            tempHead -= mPeriodNs;
                        } else {
                            tempHead += mPeriodNs;
                        }
                        if (tempHead == HEAD_MIDDLE) {
                            mHeadDisposable.dispose();
                        }
                        config(0, tempHead, PERIOD_NS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }


    // 加载本地方法
    static {
        System.loadLibrary("Pwm_Motor");
    }

    /**
     * 功能：	 打开对应的Pwm Motor设备
     * 参数：
     * dev：	设备文件名（linux系统中为设备文件描述符）
     * 返回：	true：打开成功； false：打开失败
     */
    private native boolean open();//打开


    /**
     * 功能：	 关闭对应的Pwm Motor设备
     * 参数：	无
     * 返回：	无
     */
    private native void close();

    /**
     * 功能：	配置Pwm Motor相应的属性
     */
    private native boolean config(int pwm_id, int pwm_duty_ns, int pwm_period_ns);


    /**
     * 功能：	使能Pwm Motor
     */
    private native int enable(int pwm_id);

    /**
     * 功能：	失能Pwm Motor
     */
    private native int disable(int pwm_id);
}
