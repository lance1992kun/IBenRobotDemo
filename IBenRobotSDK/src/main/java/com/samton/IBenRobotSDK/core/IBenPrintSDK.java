package com.samton.IBenRobotSDK.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;
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
public final class IBenPrintSDK {
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
                binder = (IMyBinder) iBinder;
                // 连接成功后直接连接打印机
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
    public void connectPrinter(Context context) {
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
        }
        if (!TextUtils.isEmpty(s) && binder != null) {
            binder.connectUsbPort(context, s, new UiExecute() {
                @Override
                public void onsucess() {
                    isConnected = true;
                }

                @Override
                public void onfailed() {
                    isConnected = false;
                }
            });
        }
    }

    /**
     * 蓝牙重连打印机
     */
    private void reconnectPrinter(Context context) {
        if (context != null && binder != null) {
            connectPrinter(context);
        }
    }

    /**
     * 获取打印机的连接的状态
     *
     * @return 打印机是否已经连接上
     */
    public boolean isConnect() {
        return isConnected;
    }

    /**
     * 打印方法
     *
     * @param mContext 上下文对象(用来从连打印机)
     * @param bytes    要打印的Byte数组
     */
    public void print(final Context mContext, final List<byte[]> bytes) {
        if (null != binder && null != bytes) {
            binder.clearBuffer();
            binder.writeDataByYouself(new UiExecute() {
                @Override
                public void onsucess() {
                    // 此处也可以开启读取打印机的数据
                    // 参数同样是一个实现的UiExecute接口对象
                    // 如果读的过程重出现异常，可以判断连接也发生异常，已经断开
                    // 这个读取的方法中，会一直在一条子线程中执行读取打印机发生的数据，
                    // 直到连接断开或异常才结束，并执行onFailed
                    binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), new UiExecute() {
                        @Override
                        public void onsucess() {
                            binder.acceptdatafromprinter(new UiExecute() {
                                @Override
                                public void onsucess() {
                                }

                                @Override
                                public void onfailed() {
                                    isConnected = false;
                                }
                            });
                        }

                        @Override
                        public void onfailed() {
                            reconnectPrinter(mContext);
                        }
                    });
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
}
