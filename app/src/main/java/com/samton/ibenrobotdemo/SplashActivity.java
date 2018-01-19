package com.samton.ibenrobotdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import com.samton.IBenRobotSDK.core.IBenMoveSDK;
import com.samton.IBenRobotSDK.utils.CacheUtils;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.NetworkUtils;
import com.samton.IBenRobotSDK.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener, IBenMoveSDK.ConnectCallBack {

    private SparseArray<String> mAvailableIps;
    private int mAvailableIndex = 0;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAvailableIps = new SparseArray<>();
        mAvailableIndex = 0;
        initView();
        //initData();
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    private void initData() {
        if (TextUtils.isEmpty(CacheUtils.getInstance().getString("ip"))) {
            findRobot();
        } else {
            connectRobot();
        }
    }

    private void connectRobot() {
        if (!TextUtils.isEmpty(CacheUtils.getInstance().getString("ip"))) {
            IBenMoveSDK.getInstance().connectRobot(CacheUtils.getInstance().getString("ip"), 1445, this);
        } else {
            if (mAvailableIps.size() > 0 && mCurrentIndex <= mAvailableIps.size()) {
                IBenMoveSDK.getInstance().connectRobot(mAvailableIps.get(mCurrentIndex), 1445, this);
            } else {
                findRobot();
            }
        }
    }

    private void findRobot() {
        mAvailableIps.clear();
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                String ipAddress = NetworkUtils.getIPAddress(true);
                // split>>>"."
                if (!TextUtils.isEmpty(ipAddress)) {
                    String[] ipString = ipAddress.split("\\.");
                    final String first = ipString[0];
                    final String second = ipString[1];
                    final String third = ipString[2];
                    for (int i = 100; i < 256; i++) {
                        final int index = i;
                        new Thread(new Runnable() {
                            @Override
                            public synchronized void run() {
                                String temp = first + "." + second + "." + third + "." + index;
                                if (NetworkUtils.isAvailableByPing(temp)) {
                                    mAvailableIps.put(mAvailableIndex++, temp);
                                }
                            }
                        }).start();
                    }
                }
                try {
                    Thread.sleep(10000);
                    connectRobot();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                String result = "";
                // show available ips
                for (int i = 0; i < mAvailableIps.size(); i++) {
                    result += "第" + i + "个能PING通的IP>>>" + mAvailableIps.get(i) + "\n";
                    LogUtils.e("第" + i + "个能PING通的IP>>>" + mAvailableIps.get(i));
                }
                ToastUtils.showLong(result);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent();
        switch (v.getId()) {
            // 机器人测试界面
            case R.id.button:
                mIntent.setClass(this, RobotTestActivity.class);
                break;
            // 舵机等测试界面
            case R.id.button2:
                mIntent.setClass(this, MainActivity.class);
                break;
        }
        startActivity(mIntent);
    }

    @Override
    public void onConnectSuccess() {
        if (mAvailableIps.size() > 0) {
            CacheUtils.getInstance().put("ip", mAvailableIps.get(mCurrentIndex));
        }
        LogUtils.e("连接机器人成功,机器人IP>>>" + CacheUtils.getInstance().getString("ip"));
        ToastUtils.showShort("连接机器人成功,机器人IP>>>" + CacheUtils.getInstance().getString("ip"));
    }

    @Override
    public void onConnectFailed() {
        if (mAvailableIps.size() > 0) {
            LogUtils.e("连接机器人失败,当前尝试连接的IP>>>" + mAvailableIps.get(mCurrentIndex));
            ToastUtils.showShort("连接机器人失败,当前尝试连接的IP>>>" + mAvailableIps.get(mCurrentIndex));
            mCurrentIndex++;
        } else {
            LogUtils.e("连接机器人失败,当前尝试连接的IP>>>" + CacheUtils.getInstance().getString("ip"));
            ToastUtils.showShort("连接机器人失败,当前尝试连接的IP>>>" + CacheUtils.getInstance().getString("ip"));
        }
        try {
            Thread.sleep(3000);
            connectRobot();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
