package com.samton.ibenrobotdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.samton.IBenRobotSDK.core.IBenMoveSDK;
import com.samton.ibenrobotdemo.R;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/05 10:52
 *   desc    : 机器人测试界面
 *   version : 1.0
 * </pre>
 */

public class RobotTestActivity extends AppCompatActivity
        implements View.OnClickListener, IBenMoveSDK.ConnectCallBack {

    /**
     * 机器人状态显示
     */
    private TextView mRobotStatus;
    /**
     * 移动SDK
     */
    private IBenMoveSDK moveSDK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_test);
        initView();
        initData();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        mRobotStatus = (TextView) findViewById(R.id.mRobotStatus);

        findViewById(R.id.mConnectBtn).setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        moveSDK = IBenMoveSDK.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mConnectBtn:
                moveSDK.connectRobot("192.168.11.1", 1445, this);
                break;
            default:
                break;
        }
    }

    /**
     * 机器人连接成功
     */
    @Override
    public void onConnectSuccess() {
        moveSDK.getBatteryInfo(new IBenMoveSDK.GetBatteryCallBack() {
            @Override
            public void onSuccess(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = "机器人连接成功";
                        mRobotStatus.setText(result + "\n" + msg);
                    }
                });
            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 机器人连接失败
     */
    @Override
    public void onConnectFailed() {
        mRobotStatus.setText("机器人连接失败");
    }
}
