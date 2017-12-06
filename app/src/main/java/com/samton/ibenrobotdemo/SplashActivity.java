package com.samton.ibenrobotdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
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
            // SOCKET测试界面
            case R.id.button3:
                mIntent.setClass(this, SocketTestActivity.class);
                break;
        }
        startActivity(mIntent);
    }
}
