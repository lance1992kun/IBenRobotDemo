package com.samton.IBenRobotSDK.print;

import android.content.Context;

import com.samton.IBenRobotSDK.utils.LogUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.utils.DataForSendToPrinterPos80;

import java.util.List;

/**
 * Created by lhg on 2017/10/14.
 * <p>
 * 打印机连接管理类
 */
public class PrintManager {
    public static PrintManager manger;
    private PrintUsbAdmin mPrintUsbAdmin;
    // 0  没有选择  1 USB连接  2 蓝牙连接
    private int PRINT_CONNECT_TYPE = 0;

    private PrintManager() {
    }

    /**
     * 单例 打印管理对象
     *
     * @return
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
     * @param mContext
     */
    public void initUSB(Context mContext) {
        PRINT_CONNECT_TYPE = 1;
        mPrintUsbAdmin = new PrintUsbAdmin(mContext);
    }

    /**
     * 初始化 蓝牙连接打印接
     *
     * @param mContext
     */
    public void initBLUE(Context mContext, IMyBinder binder, PrintConnectCallBack callBack) {
        PRINT_CONNECT_TYPE = 2;
        PrintBlueAdmin.getIntance().connectBlue(mContext, binder, callBack);
    }

    /**
     * 打开USB连接
     */
    public void USBConnect() {
        if (PRINT_CONNECT_TYPE == 1) {
            if (mPrintUsbAdmin != null) {
                mPrintUsbAdmin.Openusb();
            }
        } else {
            LogUtils.d("USBConnect: 未初始化USB 连接 ");
        }
    }

    /**
     * 关闭USB连接
     */
    public void USBClose() {
        if (mPrintUsbAdmin != null) {
            mPrintUsbAdmin.Closeusb();
        }
    }

    /**
     * 发送打印命令
     *
     * @param content
     */
    public void USBPrint(List<byte[]> content) {
        if(mPrintUsbAdmin!=null){
            for (int i = 0; i < content.size(); i++) {
                mPrintUsbAdmin.sendCommand(content.get(i));
            }
            mPrintUsbAdmin.sendCommand(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66, 1));
        }
    }

    /**
     * 获取USB 连接状态
     *
     * @return
     */
    public boolean getUsbConnectState() {
        return mPrintUsbAdmin.GetUsbStatus();
    }

    /**
     * 蓝牙连接
     *
     * @param mContext
     * @param binder
     * @param callBack
     */
    public void BuleConnect(Context mContext, IMyBinder binder, PrintConnectCallBack callBack) {
        PrintBlueAdmin.getIntance().connectBlue(mContext, binder, callBack);
    }

    /**
     * 蓝牙打印
     *
     * @param binder
     * @param content
     */
    public void BluePrint(IMyBinder binder, final List<byte[]> content) {
        PrintBlueAdmin.getIntance().print(content, binder);
    }

    /**
     * 获取蓝牙连接状态
     *
     * @return
     */
    public boolean getBlueConnectState() {
        return PrintBlueAdmin.getIntance().isConnect();
    }
}
