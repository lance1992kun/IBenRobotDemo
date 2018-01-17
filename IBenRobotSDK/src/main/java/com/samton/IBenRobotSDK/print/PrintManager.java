package com.samton.IBenRobotSDK.print;

import android.content.Context;

import java.util.List;

/**
 * <pre>
 *   author  : lhg
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/01/05 13:37
 *   desc    : 打印机管理类
 *   version : 1.0
 * </pre>
 */
public class PrintManager {
    /**
     * 打印机管理类单例
     */
    public volatile static PrintManager manger;
    /**
     * 打印机 - USB连接对象
     */
    private PrintUsbAdmin mPrintUsbAdmin;

    /**
     * 私有构造
     */
    private PrintManager() {

    }

    /**
     * 获取打印管理对象单例
     *
     * @return 打印管理对象单例
     */
    public static synchronized PrintManager getInstance() {
        if (manger == null) {
            synchronized (PrintManager.class) {
                if (manger == null) {
                    manger = new PrintManager();
                }
            }
        }
        return manger;
    }

    /**
     * 初始化 usb 连接打印机
     *
     * @param mContext 上下文对象
     */
    public void init(Context mContext) {
        if (mPrintUsbAdmin == null) {
            mPrintUsbAdmin = new PrintUsbAdmin(mContext);
        }
        connect();
    }

    /**
     * 打开USB连接
     */
    private void connect() {
        if (mPrintUsbAdmin != null) {
            mPrintUsbAdmin.openUsbDevice();
        }
    }

    /**
     * 关闭USB连接
     */
    public void close() {
        if (mPrintUsbAdmin != null) {
            mPrintUsbAdmin.closeUsbDevice();
        }
    }

    /**
     * 发送打印命令
     *
     * @param content 打印的内容
     */
    public void print(List<byte[]> content) {
        if (mPrintUsbAdmin != null) {
            for (int i = 0; i < content.size(); i++) {
                mPrintUsbAdmin.sendCommand(content.get(i));
            }
        }
    }

    /**
     * 获取连接状态
     *
     * @return 是否已经连接到了打印机
     */
    public boolean isPrinterConnected() {
        return mPrintUsbAdmin != null && mPrintUsbAdmin.isUsbConnected();
    }

    /**
     * 反注册广播
     */
    public void unRegister(){
        mPrintUsbAdmin.unRegister();
    }
}

