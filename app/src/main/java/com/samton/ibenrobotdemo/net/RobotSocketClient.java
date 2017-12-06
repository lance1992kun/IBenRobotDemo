package com.samton.ibenrobotdemo.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cdy on 2017/7/15.
 */

public class RobotSocketClient {
    private static final String TAG = "RobotSocketClient";

    private static RobotSocketClient mInstance;
    /**
     * 绑定服务的Socket
     */
    private Socket server;
    /**
     * 发送消息给Server
     */
    private PrintWriter out;
    /**
     * 处理消息的线程池
     */
    private ExecutorService mThreadPool;
    /**
     * 消息回调
     */
    private IMessageCallBack mCallBack;

    private void RobotSocketClient(){
        mThreadPool = Executors.newFixedThreadPool(10);
    }

    public static RobotSocketClient getmInstance(){
        if(null == mInstance){
          synchronized (RobotSocketClient.class){
           if(null == mInstance){
               mInstance = new  RobotSocketClient();
           }
          }
        }
        return mInstance;
    }

    /**
     * 连接Server
     * @param ip server IP
     * @param port server 端口
     * @throws Exception
     */
    public void startSocket(String ip, int port) throws Exception {
        SocketAddress socketAddress = new InetSocketAddress(ip, port);
        server = new Socket();
        server.connect(socketAddress,10*1000);
        if(server.isConnected()){
            if(null == mThreadPool){
                mThreadPool = Executors.newFixedThreadPool(10);
            }
            mThreadPool.execute(new WorkThread(server));
            out = new PrintWriter(server.getOutputStream());
        }
    }

    /**
     * 设置消息回调
     * @param mCallBack
     */
    public void setCallBack(IMessageCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }


    /**
     * 向Server发送消息
     * @param message
     */
    public void sendMessage(String message){
        String command = message;
        if(null != out){
            out.println(command);
            out.flush();
        }
    }

    /**
     * 处理Server回写数据
     */
    class WorkThread implements Runnable {
        private Socket socket;

        public WorkThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                while (true) {
                    String str = in.readLine();
                    if(!TextUtils.isEmpty(str)){
                        if(null != mCallBack){
                            String result = str.replaceAll("!message:", "");
                            mCallBack.onMessage(result);
                        }
                        Log.i(TAG,"socket return:" + str);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭Socket
     */
    public void close(){
        try {
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IMessageCallBack{
        void onMessage(String returnMessage);
    }
}
