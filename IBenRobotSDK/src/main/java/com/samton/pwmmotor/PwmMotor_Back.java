package com.samton.pwmmotor;

import android.os.Handler;
import android.os.Message;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/08/15
 *     desc   : 舵机管理器
 *     version: 1.0
 * </pre>
 */
@Deprecated
public class PwmMotor_Back {
    private static final PwmMotor_Back ourInstance = new PwmMotor_Back();

    public static PwmMotor_Back getInstance() {
        return ourInstance;
    }

    private PwmMotor_Back() {
    }
    /**
     * 左胳膊是否到了90度
     */
    private boolean isLeftArmUp = false;
    /**
     * 右胳膊是否到了90度
     */
    private boolean isRightArmUp = false;
    /**
     * 左胳膊缓慢下降
     */
    private static final int ARM_LEFT = 0x0;
    /**
     * 右胳膊缓慢下降
     */
    private static final int ARM_RIGHT = 0x1;
    /**
     * 头部从左边到中间
     */
    private static final int HEAD_LEFT_MIDDLE = 0x2;
    /**
     * 头部从左边到右边
     */
    private static final int HEAD_LEFT_RIGHT = 0x3;
    /**
     * 头部从中间到左边
     */
    private static final int HEAD_MIDDLE_LEFT = 0x4;
    /**
     * 头部从中间到右边
     */
    private static final int HEAD_MIDDLE_RIGHT = 0x5;
    /**
     * 头部从右边到中间
     */
    private static final int HEAD_RIGHT_MIDDLE = 0x6;
    /**
     * 头部从右边到左边
     */
    private static final int HEAD_RIGHT_LEFT = 0x7;
    /**
     * 头部间隔时间
     */
    private int mHeadPeriodTime = 150;
    /**
     * 间隔时间(纳秒)
     */
    private int mDutyNs = 100000;
    /**
     * 周期
     */
    private int periodNs = 20000000;
    /**
     * 左手时间
     */
    private int mDutyNsLeft = 15 * 100000;
    /**
     * 右手时间
     */
    private int mDutyNsRight = 16 * 100000;
    /**
     * 头部从左转到中间的时间
     */
    private int mDutyNsHeadLeft = 20 * 100000;
    /**
     * 头部到中间时间
     */
    private int mDutyNsHeadMiddle = 16 * 100000;
    /**
     * 头部到右边时间
     */
    private int mDutyNsHeadRight = 12 * 100000;
    /**
     * 延时调节的Handler
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                // 左胳膊缓慢下降
                case ARM_LEFT:
                    mDutyNsLeft -= mDutyNs;
                    // 调节左手
                    config(3, mDutyNsLeft, periodNs);
                    if (mDutyNsLeft != 5 * 100000) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(ARM_LEFT), 80);
                    } else {
                        mDutyNsLeft = 15 * 100000;
                    }
                    break;
                // 右胳膊缓慢下降
                case ARM_RIGHT:
                    mDutyNsRight += mDutyNs;
                    // 调节右手
                    config(2, mDutyNsRight, periodNs);
                    if (mDutyNsRight != 25 * 100000) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(ARM_RIGHT), 80);
                    } else {
                        mDutyNsRight = 16 * 100000;
                    }
                    break;
                // 头部从左边到中间
                case HEAD_LEFT_MIDDLE:
                    mDutyNsHeadLeft -= mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadLeft, periodNs);
                    if (mDutyNsHeadLeft != mDutyNsHeadMiddle) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_LEFT_MIDDLE), mHeadPeriodTime * 2);
                    } else {
                        mDutyNsHeadLeft = 20 * 100000;
                    }
                    break;
                // 头部从左边到右边
                case HEAD_LEFT_RIGHT:
                    mDutyNsHeadLeft -= mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadLeft, periodNs);
                    if (mDutyNsHeadLeft != mDutyNsHeadRight) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_LEFT_RIGHT), mHeadPeriodTime);
                    } else {
                        mDutyNsHeadLeft = 20 * 100000;
                    }
                    break;
                // 头部从中间到左边
                case HEAD_MIDDLE_LEFT:
                    mDutyNsHeadMiddle += mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadMiddle, periodNs);
                    if (mDutyNsHeadMiddle != mDutyNsHeadLeft) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_MIDDLE_LEFT), mHeadPeriodTime * 2);
                    } else {
                        mDutyNsHeadMiddle = 16 * 100000;
                    }
                    break;
                // 头部从中间到右边
                case HEAD_MIDDLE_RIGHT:
                    mDutyNsHeadMiddle -= mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadMiddle, periodNs);
                    if (mDutyNsHeadMiddle != mDutyNsHeadRight) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_MIDDLE_RIGHT), mHeadPeriodTime * 2);
                    } else {
                        mDutyNsHeadMiddle = 16 * 100000;
                    }
                    break;
                // 头部从右边到中间
                case HEAD_RIGHT_MIDDLE:
                    mDutyNsHeadRight += mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadRight, periodNs);
                    if (mDutyNsHeadRight != mDutyNsHeadMiddle) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_RIGHT_MIDDLE), mHeadPeriodTime * 2);
                    } else {
                        mDutyNsHeadRight = 12 * 100000;
                    }
                    break;
                // 头部从右边到左边
                case HEAD_RIGHT_LEFT:
                    mDutyNsHeadRight += mDutyNs;
                    // 调节头部
                    config(0, mDutyNsHeadRight, periodNs);
                    if (mDutyNsHeadRight != mDutyNsHeadLeft) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(HEAD_RIGHT_LEFT), mHeadPeriodTime);
                    } else {
                        mDutyNsHeadRight = 12 * 100000;
                    }
                    break;
            }
            return false;
        }
    });


    /**
     * 打开设备
     */
    public boolean openDevices() {
        return open();
    }

    /**
     * 关闭设备
     */
    public void closeDevices() {
        close();
    }

    /**
     * 左胳膊上抬
     */
    public void leftArmUp() {
        // 如果机器人左胳膊在上面则不动
        if (!isLeftArmUp) {
            config(3, 15 * 100000, periodNs);
            isLeftArmUp = true;
        }
    }

    /**
     * 左胳膊下降
     */
    public void leftArmDown() {
        // 如果机器人左胳膊在下面则不动
        if (isLeftArmUp) {
            mHandler.sendEmptyMessage(ARM_LEFT);
            isLeftArmUp = false;
        }
    }

    /**
     * 右胳膊上抬
     */
    public void rightArmUp() {
        // 如果机器人右胳膊在上面则不动
        if (!isRightArmUp) {
            config(2, 16 * 100000, periodNs);
            isRightArmUp = true;
        }
    }

    /**
     * 右胳膊下降
     */
    public void rightArmDown() {
        // 如果机器人右胳膊在下面则不动
        if (isRightArmUp) {
            mHandler.sendEmptyMessage(ARM_RIGHT);
            isRightArmUp = false;
        }
    }

//    /**
//     * 头部从左侧转到中间
//     */
//    public void headLeft2Middle() {
//        mHandler.sendEmptyMessage(HEAD_LEFT_MIDDLE);
//    }
//
//    /**
//     * 头部从左侧到右侧
//     */
//    public void headLeft2Right() {
//        mHandler.sendEmptyMessage(HEAD_LEFT_RIGHT);
//    }
//
//    /**
//     * 头部从中间到左边
//     */
//    public void headMiddle2Left() {
//        mHandler.sendEmptyMessage(HEAD_MIDDLE_LEFT);
//    }
//
//    /**
//     * 头部从中间到右边
//     */
//    public void headMiddle2Right() {
//        mHandler.sendEmptyMessage(HEAD_MIDDLE_RIGHT);
//    }
//
//    /**
//     * 头部从右侧到中间
//     */
//    public void headRight2Middle() {
//        mHandler.sendEmptyMessage(HEAD_RIGHT_MIDDLE);
//    }
//
//    /**
//     * 头部从右侧到左边
//     */
//    public void headRight2Left() {
//        mHandler.sendEmptyMessage(HEAD_RIGHT_LEFT);
//    }

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
    public native boolean config(int pwm_id, int pwm_duty_ns, int pwm_period_ns);


    /**
     * 功能：	使能Pwm Motor
     */
    private native int enable(int pwm_id);

    /**
     * 功能：	失能Pwm Motor
     */
    private native int disable(int pwm_id);
}
