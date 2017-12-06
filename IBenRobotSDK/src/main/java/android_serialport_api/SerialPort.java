/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android_serialport_api;

import android.util.Log;

import com.samton.IBenRobotSDK.utils.LogUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.Runtime.getRuntime;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/06/28
 *     desc   : 串口对象
 *     version: 1.0
 * </pre>
 */
class SerialPort {

    /**
     * 打印标识
     */
    private static final String TAG = "SerialPort";

    /**
     * Google指定不能动的变量名
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    /**
     * 构造函数
     *
     * @param device   物理地址
     * @param baudRate 波特率
     * @param flags    标识位
     * @throws SecurityException 权限异常
     * @throws IOException       读取异常
     */
    SerialPort(File device, int baudRate, int flags) throws SecurityException, IOException {

		/* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            try {
                Process su = null;
                int state = getRuntime().exec("su").waitFor();
                LogUtils.e(state + "-----------------");
                if (state == 0) {
                    su = getRuntime().exec("chmod 777" + device.getAbsolutePath());
                    LogUtils.e("*************************");
                }
                // /system/user/bin      && " + "chmod 777 " + device.getAbsolutePath() + "&&" + "exit
//                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
//                su.getOutputStream().write(cmd.getBytes());
                if ((su == null || su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    LogUtils.e("+++++++++++++++++++++++++++++");
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }

        mFd = open(device.getAbsolutePath(), baudRate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    /**
     * 获取输入流
     *
     * @return 输入流对象
     */
    InputStream getInputStream() {
        return mFileInputStream;
    }

    /**
     * 获取输出流
     *
     * @return 输出流
     */
    OutputStream getOutputStream() {
        return mFileOutputStream;
    }


    /**
     * Native方法 开启串口
     *
     * @param path     设备地址
     * @param baudrate 波特率
     * @param flags    标识位
     * @return 文件描述对象
     */
    private native static FileDescriptor open(String path, int baudrate, int flags);

    /**
     * Native方法关闭串口
     */
    public native void close();

    /**
     * 加载本地方法
     */
    static {
        System.loadLibrary("serial_port");
    }
}
