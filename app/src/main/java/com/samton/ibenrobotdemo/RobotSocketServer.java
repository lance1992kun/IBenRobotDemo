package com.samton.ibenrobotdemo;

import android.text.TextUtils;

import com.samton.IBenRobotSDK.utils.FileIOUtils;
import com.samton.IBenRobotSDK.utils.FileUtils;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.NetworkUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/29
 *     desc   : 本地服务器类
 *     version: 1.0
 * </pre>
 */

public class RobotSocketServer {
    /**
     * 监听本地的ServerSocket
     */
    private ServerSocket mServerSocket = null;
    /**
     * 最后一次连接的Socket，消息只会回调给最后一次连接的客户端
     */
    private Socket mLastSocket = null;
    /**
     * 本地监听的端口
     */
    private int mPort = 0;
    /**
     * 调用者消息回调
     */
    private MessageCallBack mCallBack = null;

    /**
     * 构造函数
     *
     * @param mPort 端口号
     */
    public RobotSocketServer(int mPort) {
        this.mPort = mPort;
    }

    /**
     * 开启本地监听
     */
    public void startServer() {
        if (mServerSocket == null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        mServerSocket = new ServerSocket(mPort);
                        LogUtils.e("本地SOCKET已经开启IP>>>" + NetworkUtils.getIPAddress(true) + "端口为>>>" + mPort);
                        while (!mServerSocket.isClosed()) {
                            // 接受客户端请求
                            if (mLastSocket == null) {
                                mLastSocket = mServerSocket.accept();
                            } else {
                                if (mLastSocket.isClosed() || !mLastSocket.isConnected()) {
                                    mLastSocket = mServerSocket.accept();
                                }
                            }
                            LogUtils.e("连接的Socket>>>" + mLastSocket.getInetAddress());
                            try {
                                String readResult;
                                // 输入流>>>用于接收客户端信息
                                BufferedReader mReader = new BufferedReader(new InputStreamReader(mLastSocket.getInputStream()));
                                while ((readResult = mReader.readLine()) != null) {
                                    // 读取客户端发送过来的信息
                                    // readResult = mReader.readLine();
                                    if (!TextUtils.isEmpty(readResult)) {
                                        // 回调系统得到的客户端信息
                                        mCallBack.onReceive(readResult);
                                    }
                                }
                                mLastSocket.close();
                                mLastSocket = null;
                            } catch (Exception ex) {
                                mLastSocket.close();
                                ex.printStackTrace();
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LogUtils.e(throwable.getMessage());
                }
            });
        }
    }


    /**
     * 设置消息回调
     *
     * @param mCallBack 回调函数
     */
    public void setCallBack(MessageCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    /**
     * 发送消息给客户端
     *
     * @param msg 要发给客户端的消息
     */
    public void sendMessage(String msg) {
        PrintWriter mPrintWriter;
        try {
            mPrintWriter = new PrintWriter(mLastSocket.getOutputStream());
            // 向客户端发送信息
            mPrintWriter.println(msg);
            mPrintWriter.flush();
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }
    }

    /**
     * 向客户端发送文件
     *
     * @param file 地图文件
     */
    public void sendMap(File file) {
        DataOutputStream mDataOutputStream;
        if (!mLastSocket.isClosed() && mLastSocket.isConnected()) {
            try {
                mDataOutputStream = new DataOutputStream(mLastSocket.getOutputStream());
                if (FileUtils.isFileExists(file)) {
                    // 开始传输文件
                    LogUtils.e("开始传输文件");
                    byte[] bytes = FileIOUtils.readFile2BytesByChannel(file);
                    if (bytes != null) {
                        mDataOutputStream.write(bytes);
                        mDataOutputStream.flush();
                    }
                    LogUtils.e("传输文件完成");
                    // 关闭流
                    mDataOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止本地服务
     */
    public void shutDownServer() {
        if (mServerSocket != null) {
            try {
                mServerSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回调信息
     */
    public interface MessageCallBack {
        /**
         * 回调信息
         *
         * @param message 信息
         */
        void onReceive(String message);
    }
}
