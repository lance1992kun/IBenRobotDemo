package com.samton.serialport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/06/28
 *     desc   : 串口操作工具
 *     version: 1.0
 * </pre>
 */
public class SerialUtil {
    /**
     * 串口对象
     */
    private SerialPort mSerialPort;
    /**
     * 输入流(用于数据读取)
     */
    private InputStream mInputStream;
    /**
     * 输出流(用于数据写入)
     */
    private OutputStream mOutputStream;
    /**
     * 回写长度
     */
    private volatile int size = -1;
    /**
     * 缓冲区大小
     */
    private static final int MAX = 512;

    /**
     * 构造函数
     *
     * @param path 串口的物理地址
     */
    public SerialUtil(String path) {
//        try {
//            mSerialPort = new SerialPort(new File(path), baudRate, flags);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (mSerialPort != null) {
//            // 设置读、写
//            mInputStream = mSerialPort.getInputStream();
//            mOutputStream = mSerialPort.getOutputStream();
//        } else {
//            throw new NullPointerException("串口设置有误");
//        }
        mSerialPort = new SerialPort();
        mSerialPort.open(new File(path), SerialPort.BAUDRATE.B115200, SerialPort.STOPB.B1
                , SerialPort.DATAB.CS8, SerialPort.PARITY.NONE, SerialPort.FLOWCON.NONE);
        mInputStream = mSerialPort.getInputStream();
        mOutputStream = mSerialPort.getOutputStream();
    }

    /**
     * 取得byte的长度
     *
     * @return 长度
     */
    public int getSize() {
        return size;
    }

    /**
     * 串口读数据
     *
     * @return 回写的byte数组
     */
    public synchronized byte[] getData() throws NullPointerException {
        //上锁，每次只能一个线程在取得数据
        try {
            byte[] buffer = new byte[MAX];
            if (mInputStream == null) {
                throw new NullPointerException("mInputStream is null");
            }
            //一次最多可读Max的长度
            size = mInputStream.read(buffer);
            if (size > 0) return buffer;
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从串口读取数据
     *
     * @return 回显的数组
     * @throws NullPointerException 空指针异常
     */
    public synchronized byte[] getDataByte() throws NullPointerException {
        byte[] buffer = new byte[MAX];
        if (mInputStream == null) {
            throw new NullPointerException("is null");
        }
        try {
            if (mInputStream.available() > 0 && mInputStream.read(buffer) > 0) {
//                mInputStream.read(buffer);
                return buffer;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向串口上写数据
     *
     * @param data 显示的16进制的字符串
     */
    public synchronized void setData(byte[] data) throws NullPointerException {
        if (mOutputStream == null) throw new NullPointerException("outputStream为空");
        try {
            mOutputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
