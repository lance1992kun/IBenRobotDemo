package com.samton.IBenRobotSDK.print;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.samton.IBenRobotSDK.utils.LogUtils;

import java.util.HashMap;

/**
 * <pre>
 *   author  : lhg
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/01/05 13:37
 *   desc    : 打印机USB连接管理类
 *   version : 1.0
 * </pre>
 */
@SuppressLint("NewApi")
public class PrintUsbAdmin {
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * Android - USB管理对象
     */
    private UsbManager mUsbManager;
    /**
     * Android - USB-Device对象
     */
    private UsbDevice mDevice;
    /**
     * Android - USB-连接对象
     */
    private UsbDeviceConnection mConnection;
    /**
     * Android - USB-终端对象
     */
    private UsbEndpoint mEndpoint;
    /**
     * 跳转
     */
    private static PendingIntent mPermissionIntent = null;
    /**
     * 自定义的permission
     */
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    /**
     * 广播接受者
     */
    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            setDevice(device);
                        } else {
                            closeUsbDevice();
                            mDevice = null;
                        }
                    }
                }
            }
        }
    };
    /**
     * 设置USB设备
     *
     * @param device USB设备
     */
    private void setDevice(UsbDevice device) {
        if (device != null) {
            UsbInterface mUsbInterface = null;
            UsbEndpoint mUsbEndpoint = null;
            int InterfaceCount = device.getInterfaceCount();
            int j;
            mDevice = device;
            for (j = 0; j < InterfaceCount; j++) {
                int i;
                mUsbInterface = device.getInterface(j);
                LogUtils.d("接口是--->" + j + "类是--->" + mUsbInterface.getInterfaceClass());
                if (mUsbInterface.getInterfaceClass() == 7) {
                    int UsbEndpointCount = mUsbInterface.getEndpointCount();
                    for (i = 0; i < UsbEndpointCount; i++) {
                        mUsbEndpoint = mUsbInterface.getEndpoint(i);
                        LogUtils.d("端点是:" + i + "方向是:" + mUsbEndpoint.getDirection() + "类型是:" + mUsbEndpoint.getType());
                        if (mUsbEndpoint.getDirection() == 0 && mUsbEndpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            LogUtils.d("接口是:" + j + "端点是:" + i);
                            break;
                        }
                    }
                    if (i != UsbEndpointCount) {
                        break;
                    }
                }
            }
            if (j == InterfaceCount) {
                LogUtils.d("没有打印机接口");
                return;
            }
            mEndpoint = mUsbEndpoint;
            UsbDeviceConnection connection = mUsbManager.openDevice(device);
            if (connection != null && connection.claimInterface(mUsbInterface, true)) {
                LogUtils.d("USB设备打开成功！ ");
                mConnection = connection;
            } else {
                LogUtils.d("USB设备打开失败！ ");
                mConnection = null;
            }
        }
    }

    /**
     * 打开USB设备
     */
    public void openUsbDevice() {
        if (mDevice != null) {
            setDevice(mDevice);
            if (mConnection == null) {
                HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
                for (UsbDevice device : deviceList.values()) {
                    mUsbManager.requestPermission(device, mPermissionIntent);
                }
            }
        } else {
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            for (UsbDevice device : deviceList.values()) {
                mUsbManager.requestPermission(device, mPermissionIntent);
            }
        }
    }

    /**
     * 关闭USB设备
     */
    public void closeUsbDevice() {
        if (mConnection != null) {
            mConnection.close();
            mConnection = null;
        }
    }

    /**
     * 获取USB连接状态
     *
     * @return 是否连接
     */
    public boolean isUsbConnected() {
        return mConnection == null;
    }

    /**
     * 发送要打印的数据
     *
     * @param content 要打印的数据
     * @return 成功或者失败
     */
    public boolean sendCommand(byte[] content) {
        boolean isSuccess;
        synchronized (this) {
            int len = -1;
            if (mConnection != null) {
                len = mConnection.bulkTransfer(mEndpoint, content, content.length, 10000);
            }
            if (len < 0) {
                isSuccess = false;
            } else {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public PrintUsbAdmin(Context context) {
        this.mContext = context;
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        context.registerReceiver(mUsbReceiver, filter);
    }

    /**
     * 反注册广播
     */
    public void unRegister(){
        mContext.unregisterReceiver(mUsbReceiver);
    }
}