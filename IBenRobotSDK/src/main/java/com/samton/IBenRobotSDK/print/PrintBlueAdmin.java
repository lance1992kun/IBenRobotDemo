package com.samton.IBenRobotSDK.print;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.samton.IBenRobotSDK.utils.LogUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.DataForSendToPrinterPos80;

import java.util.List;


/**
 * 打印机相关类
 * Created by lhg on 2017/6/12.
 */
class PrintBlueAdmin {
    public static PrintBlueAdmin utils;

    private PrintBlueAdmin() {

    }

    public static synchronized PrintBlueAdmin getIntance() {
        if (utils == null) {
            utils = new PrintBlueAdmin();
        }
        return utils;
    }

    /**
     * 打印机是否链接
     */
    public boolean printIsConnect = false;

    /**
     * 获取打印机地址
     *
     * @param context 上下文对象
     * @return 打印机地址
     */
    public String getPrinterAddress(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Print", "getPrinterAddress: ");
            return null;
        }
        String appKey = appInfo.metaData.getString(PrintConstants.PRINT_ADDRESS);
        return appKey;
    }

    /**
     * 蓝牙连接打印机
     */
    @SuppressWarnings("MissingPermission")
    public void connectBlue(final Context context, final IMyBinder binder, final PrintConnectCallBack callBack) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter && !adapter.isEnabled()) {
            LogUtils.e("请开启蓝牙之后再连接");
            return;
        }
        // 获取打印机地址
        final String address = getPrinterAddress(context);
        if (!TextUtils.isEmpty(address) && null != binder) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    binder.connectBtPort(address, new UiExecute() {
                        @Override
                        public void onsucess() {
                            printIsConnect = true;
                            //此处也可以开启读取打印机的数据
                            //参数同样是一个实现的UiExecute接口对象
                            //如果读的过程重出现异常，可以判断连接也发生异常，已经断开
                            //这个读取的方法中，会一直在一条子线程中执行读取打印机发生的数据，
                            //直到连接断开或异常才结束，并执行onfailed
                            binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), new UiExecute() {
                                @Override
                                public void onsucess() {
                                    binder.acceptdatafromprinter(new UiExecute() {
                                        @Override
                                        public void onsucess() {
                                            callBack.connectSuccess();
                                        }

                                        @Override
                                        public void onfailed() {
                                            callBack.onDisConnect();
                                            printIsConnect = false;
                                        }
                                    });
                                }

                                @Override
                                public void onfailed() {
                                    printIsConnect = false;
                                    callBack.connectFail("蓝牙连接失败 请重新连接");
                                }
                            });
                        }

                        @Override
                        public void onfailed() {
                            callBack.connectFail("蓝牙连接失败 请重新连接");
                        }

                    });
                    Looper.loop();
                }
            }).start();
        } else {
            callBack.connectFail("蓝牙连接地址设置不正确 或 binder 为空");
        }
    }

    /**
     * 获取打印机的连接的状态
     *
     * @return
     */
    public boolean isConnect() {
        return printIsConnect;
    }

    /**
     * 打印方法
     */
    public void print(final List<byte[]> bytes, IMyBinder binder) {
        if (null != binder && null != bytes) {
            binder.writeDataByYouself(new UiExecute() {
                @Override
                public void onsucess() {
                }

                @Override
                public void onfailed() {
                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    //切纸命令
                    bytes.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66, 1));
                    return bytes;
                }
            });
        }

    }
}
