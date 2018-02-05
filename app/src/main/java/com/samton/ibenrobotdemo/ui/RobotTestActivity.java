package com.samton.ibenrobotdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.samton.IBenRobotSDK.core.IBenMoveSDK;
import com.samton.IBenRobotSDK.utils.ToastUtils;
import com.samton.ibenrobotdemo.R;
import com.slamtec.slamware.action.ActionStatus;
import com.slamtec.slamware.robot.Location;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/02/05 10:52
 *   desc    : 机器人测试界面
 *   version : 1.0
 * </pre>
 */

public class RobotTestActivity extends AppCompatActivity implements
        View.OnClickListener,
        IBenMoveSDK.ConnectCallBack,
        IBenMoveSDK.MoveCallBack {

    /**
     * 机器人状态显示
     */
    private TextView mRobotStatus;
    /**
     * 指定点X
     */
    private EditText mLocationX;
    /**
     * 指定点Y
     */
    private EditText mLocationY;
    /**
     * 指定点Z
     */
    private EditText mLocationZ;
    /**
     * 指定点Yaw
     */
    private EditText mLocationYaw;
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

        mLocationX = (EditText) findViewById(R.id.mLocationX);
        mLocationY = (EditText) findViewById(R.id.mLocationY);
        mLocationZ = (EditText) findViewById(R.id.mLocationZ);
        mLocationYaw = (EditText) findViewById(R.id.mLocationYaw);

        findViewById(R.id.mConnectBtn).setOnClickListener(this);
        findViewById(R.id.mGo2LocationBtn).setOnClickListener(this);
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
            case R.id.mGo2LocationBtn:
                // 清空当前状态
                mRobotStatus.setText("");
                String x = mLocationX.getText().toString().trim();
                String y = mLocationY.getText().toString().trim();
                String z = mLocationZ.getText().toString().trim();
                String yaw = mLocationYaw.getText().toString().trim();
                if (TextUtils.isEmpty(x) || TextUtils.isEmpty(y) || TextUtils.isEmpty(yaw)) {
                    ToastUtils.showShort("请输入X，Y，Yaw");
                    return;
                }
                Location location = new Location(
                        Float.valueOf(x), Float.valueOf(y), 0);
                moveSDK.go2Location(location, Float.valueOf(yaw), this);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRobotStatus.setText("机器人连接失败");
            }
        });
    }

    @Override
    public void onStateChange(final ActionStatus status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = "到达该点的状态" + status;
                mRobotStatus.setText(msg);
            }
        });
    }
}
