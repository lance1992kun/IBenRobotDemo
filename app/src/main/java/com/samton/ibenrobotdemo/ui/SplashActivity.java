package com.samton.ibenrobotdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.samton.ibenrobotdemo.R;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        //initData();
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent();
        switch (v.getId()) {
            // 串口测试界面
            case R.id.button:
                mIntent.setClass(this, SerialTestActivity.class);
                break;
            // 舵机等测试界面
            case R.id.button2:
                mIntent.setClass(this, MainActivity.class);
                break;
            // 视频测试界面
            case R.id.button3:
                mIntent.setClass(this, VideoTestActivity.class);
                break;
            // 底盘测试界面
            case R.id.button4:
                mIntent.setClass(this, RobotTestActivity.class);
                break;
            // 音频测试界面
            case R.id.button5:
                mIntent.setClass(this, AudioTestActivity.class);
                break;
        }
        startActivity(mIntent);
    }

}
