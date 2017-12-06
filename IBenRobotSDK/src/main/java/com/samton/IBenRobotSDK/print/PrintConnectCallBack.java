package com.samton.IBenRobotSDK.print;

/**
 * Created by lhg on 2017/10/14.
 * 打印机蓝牙连接回调
 */
public interface PrintConnectCallBack {
    void connectSuccess();

    void connectFail(String errorMessage);

    void onDisConnect();
}
