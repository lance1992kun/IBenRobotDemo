package com.samton.IBenRobotSDK.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.samton.IBenRobotSDK.utils.LogUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.PosPrinterDev;

import java.util.List;


/**
 * <pre>
 *     @author : lhg
 *     time   : 2017/10/18
 *     desc   : 打印机SDK
 *     version: 1.0
 * </pre>
 */
public class IBenPrintSDK {
    /**
     * 打印机是否链接
     */
    private boolean isConnected = false;
    /**
     * 与打印机交互对象
     */
    private IMyBinder binder = null;
    /**
     * 打印SDK连接对象
     */
    private ServiceConnection mConnection = null;
    /**
     * 打印机SDK单例对象
     */
    private static IBenPrintSDK instance = null;

    /**
     * 私有构造
     */
    private IBenPrintSDK() {

    }

    /**
     * 获取打印SDK单例
     *
     * @return 打印SDK
     */
    public static synchronized IBenPrintSDK getInstance() {
        if (instance == null) {
            instance = new IBenPrintSDK();
        }
        return instance;
    }

    /**
     * 初始化打印机
     *
     * @param context 上下文对象
     */
    public void initPrinter(final Context context) {
        // 初始化连接对象
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 权宜之计，解决bug问题
                try{
                    binder = (IMyBinder) iBinder;
                }catch (Throwable throwable){
                    LogUtils.e(throwable.getMessage());
                }
                // 初始化成功后直接连接打印机
                connectPrinter(context);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        // 绑定service，获取连接对象
        Intent intent = new Intent(context, PosprinterService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解除绑定
     */
    public void unbind(Context context) {
        if (context != null && mConnection != null) {
            context.unbindService(mConnection);
        }
    }

    /**
     * 蓝牙连接打印机
     */
    public void connectPrinter(final Context context) {
        // 获取所有的USB打印机
        List<String> strings = PosPrinterDev.GetUsbPathNames(context);
        // 没有打印机的话直接返回
        if (strings == null) {
            return;
        }
        // 打印机的物理地址
        String s = null;
        if (strings.size() == 1) {
            s = strings.get(0);
            LogUtils.d("打印机物理地址--->" + s);
        }
        if (!TextUtils.isEmpty(s) && binder != null) {
            binder.connectUsbPort(context, s, new UiExecute() {
                @Override
                public void onsucess() {
                    isConnected = true;
                }

                @Override
                public void onfailed() {
                    reconnectPrinter(context);
                }
            });
        }
    }

    /**
     * 获取打印机连接状态 异步
     *
     * @param printCallBack  状态回调
     */
    public void checkLinkedState(final PrintCallBack printCallBack) {
        if (binder != null) {
            binder.checkLinkedState(new UiExecute() {
                @Override
                public void onsucess() {
                    printCallBack.isConnect(isConnected);
                }

                @Override
                public void onfailed() {
                    printCallBack.isConnect(false);
                }
            });
        }

    }

    /**
     * 蓝牙重连打印机
     */
    public void reconnectPrinter(Context context) {
        if (context != null && binder != null) {
            connectPrinter(context);
        }
    }


    /**
     * 打印方法
     *
     * @param bytes 要打印的Byte数组
     */
    public void print(final List<byte[]> bytes) {
        if (null != binder && null != bytes) {
            binder.clearBuffer();
            binder.writeDataByYouself(new UiExecute() {
                @Override
                public void onsucess() {

                }

                @Override
                public void onfailed() {
                    binder.clearBuffer();
                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    return bytes;
                }
            });
        }

    }

    public interface PrintCallBack {
        void isConnect(boolean isConnect);
    }
}
