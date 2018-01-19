package com.samton.ibenrobotdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.samton.IBenRobotSDK.core.IBenChatSDK;
import com.samton.IBenRobotSDK.core.IBenRecordUtil;
import com.samton.IBenRobotSDK.core.IBenSerialUtil;
import com.samton.IBenRobotSDK.core.IBenTTSUtil;
import com.samton.IBenRobotSDK.core.IBenTranslateSDK;
import com.samton.IBenRobotSDK.core.IBenWakeUpUtil;
import com.samton.IBenRobotSDK.core.MainSDK;
import com.samton.IBenRobotSDK.data.MessageBean;
import com.samton.IBenRobotSDK.interfaces.IBenMsgCallBack;
import com.samton.IBenRobotSDK.interfaces.IBenRecordCallBack;
import com.samton.IBenRobotSDK.interfaces.IBenTTSCallBack;
import com.samton.IBenRobotSDK.interfaces.IBenTranslateCallBack;
import com.samton.IBenRobotSDK.interfaces.ISerialCallBack;
import com.samton.IBenRobotSDK.interfaces.IWakeUpCallBack;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.ToastUtils;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ISerialCallBack {

    private EditText mSendTestEdit = null;
    private TextView mResultText = null;
    private TextView mStatusText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mResultText = (TextView) findViewById(R.id.mResultText);
        mSendTestEdit = (EditText) findViewById(R.id.mSendTestEdit);
        mStatusText = (TextView) findViewById(R.id.mStatusText);
        findViewById(R.id.mSendTestBtn).setOnClickListener(this);
        findViewById(R.id.mTTSTestBtn).setOnClickListener(this);
        findViewById(R.id.mRecordTestBtn).setOnClickListener(this);
        findViewById(R.id.mTranslateTestBtn).setOnClickListener(this);
        findViewById(R.id.mPrintTestBtn).setOnClickListener(this);

        findViewById(R.id.mLeftUp).setOnClickListener(this);
        findViewById(R.id.mLeftDown).setOnClickListener(this);
        findViewById(R.id.mRightUp).setOnClickListener(this);
        findViewById(R.id.mRightDown).setOnClickListener(this);
        findViewById(R.id.mHeadLeft).setOnClickListener(this);
        findViewById(R.id.mHeadMiddle).setOnClickListener(this);
        findViewById(R.id.mHeadRight).setOnClickListener(this);
        findViewById(R.id.mDancingTestBtn).setOnClickListener(this);
        findViewById(R.id.mSerialPortBtn).setOnClickListener(this);
    }

    private void initData() {
        MainSDK.init(getApplication());
        IBenWakeUpUtil.getInstance().setCallBack(new IWakeUpCallBack() {
            @Override
            public void onWakeUp(int angle, boolean isPassive) {
                mResultText.post(new Runnable() {
                    @Override
                    public void run() {
                        mResultText.setText("已经唤醒");
                    }
                });
            }
        });
        IBenChatSDK.getInstance().initSDK(new IBenMsgCallBack() {
            @Override
            public void onSuccess(MessageBean bean) {
                mResultText.setText(bean.getData().getAppMessage().get(0).getMessage());
                IBenTTSUtil.getInstance().startSpeaking(bean.getData().getAppMessage().get(0).getMessage(), new IBenTTSCallBack() {
                    @Override
                    public void onSpeakBegin() {

                    }

                    @Override
                    public void onCompleted(SpeechError error) {

                    }
                });
            }

            @Override
            public void onFailed(String errorMsg) {
                mResultText.setText(errorMsg);
            }

            @Override
            public void onStateChange(boolean isQA) {

            }
        });
        // IBenChatSDK.getInstance().initIMSDK(this);
        IBenTTSUtil.getInstance().init(this);
        IBenRecordUtil.getInstance().init(this);
        IBenRecordUtil.getInstance().setCallBack(new IBenRecordCallBack() {
            @Override
            public void onBeginOfSpeech() {
                mStatusText.setText("正在聆听…");
            }

            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                mStatusText.setText("聆听结束…");
            }

            @Override
            public void onResult(String result) {
                mResultText.setText("聆听结果为>>>" + result);
                LogUtils.e("聆听结果为>>>" + result);
                IBenTTSUtil.getInstance().startSpeaking(result, new IBenTTSCallBack() {
                    @Override
                    public void onSpeakBegin() {

                    }

                    @Override
                    public void onCompleted(SpeechError error) {

                    }
                });
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
        IBenTranslateSDK.getInstance().init(new IBenTranslateCallBack() {
            @Override
            public void onResult(String result) {
                LogUtils.e(result);
                IBenTTSUtil.getInstance().startSpeaking(result, new IBenTTSCallBack() {
                    @Override
                    public void onSpeakBegin() {

                    }

                    @Override
                    public void onCompleted(SpeechError error) {

                    }
                });
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

        IBenSerialUtil.getInstance().setCallBack(this);
    }


    @Override
    public void onClick(View v) {
        String msg = mSendTestEdit.getText().toString().trim();
        if (v.getId() != R.id.mRecordTestBtn && TextUtils.isEmpty(msg)) {
            ToastUtils.showShort("请输入要发送/说的话");
            return;
        }
        switch (v.getId()) {
            case R.id.mSendTestBtn:
                IBenChatSDK.getInstance().sendMessage(msg);
                break;
            case R.id.mTTSTestBtn:
                IBenTTSUtil.getInstance().startSpeaking(msg, new IBenTTSCallBack() {
                    @Override
                    public void onSpeakBegin() {

                    }

                    @Override
                    public void onCompleted(SpeechError error) {

                    }
                });
                break;
            case R.id.mRecordTestBtn:
                IBenRecordUtil.getInstance().startRecognize();
                break;
            case R.id.mTranslateTestBtn:
                IBenTranslateSDK.getInstance().translate(msg, "英文", "中文");
                break;
            case R.id.mPrintTestBtn:
                break;
            case R.id.mLeftUp:
                // PwmMotor.getInstance().leftArmUp();
                break;
            case R.id.mLeftDown:
                // PwmMotor.getInstance().leftArmDown();
                break;
            case R.id.mRightUp:
                // PwmMotor.getInstance().rightArmUp();
                break;
            case R.id.mRightDown:
                // PwmMotor.getInstance().rightArmDown();
                break;
            case R.id.mHeadLeft:
                // PwmMotor.getInstance().head2Left();
                break;
            case R.id.mHeadMiddle:
                // PwmMotor.getInstance().head2Middle();
                break;
            case R.id.mHeadRight:
                // PwmMotor.getInstance().head2Right();
                break;
            // 串口发送数据测试
            case R.id.mSerialPortBtn:
                IBenSerialUtil.getInstance().sendData(msg);
                break;
        }
    }

    @Override
    public void onReadData(String result) {
        final String text = "串口回写数据--->" + result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResultText.setText(text);
            }
        });
    }
}
