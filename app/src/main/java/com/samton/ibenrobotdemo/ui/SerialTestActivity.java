package com.samton.ibenrobotdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.samton.IBenRobotSDK.core.IBenSerialUtil;
import com.samton.IBenRobotSDK.core.MainSDK;
import com.samton.IBenRobotSDK.data.Constants;
import com.samton.IBenRobotSDK.interfaces.ISerialCallBack;
import com.samton.IBenRobotSDK.utils.FileIOUtils;
import com.samton.IBenRobotSDK.utils.FileUtils;
import com.samton.IBenRobotSDK.utils.TimeUtils;
import com.samton.ibenrobotdemo.R;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/26
 *     desc   : 舵机连接测试
 *     version: 1.0
 * </pre>
 */

public class SerialTestActivity extends AppCompatActivity implements ISerialCallBack {

    private TextView mTextView;
    private ScrollView mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_test);

        mTextView = (TextView) findViewById(R.id.mTextView);
        mContent = (ScrollView) findViewById(R.id.mContent);

        MainSDK.init(getApplication());
        IBenSerialUtil.getInstance().setCallBack(this);

        FileUtils.createOrExistsFile(Constants.MAP_PATH + "/log.txt");

        findViewById(R.id.mClearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
            }
        });
    }

    @Override
    public void onReadData(String result) {
        final String msg = TimeUtils.getNowString()
                + "---" + result.substring(0, 5) + "\n";
        FileIOUtils.writeFileFromString(
                Constants.MAP_PATH + "/log.txt", msg, true);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.append(msg);
                mContent.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
