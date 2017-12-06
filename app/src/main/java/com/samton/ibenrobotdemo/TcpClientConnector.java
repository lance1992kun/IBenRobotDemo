package com.samton.ibenrobotdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TcpClientConnector {
    private static TcpClientConnector mTcpClientConnector;
    private Socket mClient;
    private ConnectListener mListener;
    private Thread mConnectThread;

    public interface ConnectListener {
        void onReceiveData(String data);
    }

    public void setOnConnectLinstener(ConnectListener listener) {
        this.mListener = listener;
    }

    public static TcpClientConnector getInstance() {
        if (mTcpClientConnector == null)
            mTcpClientConnector = new TcpClientConnector();
        return mTcpClientConnector;
    }

    /**
     * 发送信息的Handler
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (mListener != null) {
                        mListener.onReceiveData(msg.getData().getString("data"));
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    /**
     * 创建连接
     *
     * @param mSerIP   IP
     * @param mSerPort 端口
     */
    public void createConnect(final String mSerIP, final int mSerPort) {
        if (mConnectThread == null) {
            mConnectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        connect(mSerIP, mSerPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            mConnectThread.start();
        }
    }

    /**
     * 与服务端进行连接
     *
     * @throws IOException IO异常
     */
    private void connect(String mSerIP, int mSerPort) throws IOException {
        if (mClient == null) {
            mClient = new Socket(mSerIP, mSerPort);
        }
        InputStream inputStream = mClient.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            String data = new String(buffer, 0, len);
            Message message = new Message();
            message.what = 100;
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }

    /**
     * 发送数据
     *
     * @param data 需要发送的内容
     */
    public void send(String data) throws IOException {
        OutputStream outputStream = mClient.getOutputStream();
        outputStream.write(data.getBytes());
    }

    /**
     * 断开连接
     *
     * @throws IOException IO异常
     */
    public void disconnect() throws IOException {
        if (mClient != null) {
            mClient.close();
            mClient = null;
        }
    }
}
