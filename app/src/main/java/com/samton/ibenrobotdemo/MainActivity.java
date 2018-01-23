package com.samton.ibenrobotdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.samton.IBenRobotSDK.core.IBenChatSDK;
import com.samton.IBenRobotSDK.core.IBenRecordUtil;
import com.samton.IBenRobotSDK.core.IBenSerialUtil;
import com.samton.IBenRobotSDK.core.IBenTTSUtil;
import com.samton.IBenRobotSDK.core.IBenTranslateSDK;
import com.samton.IBenRobotSDK.core.MainSDK;
import com.samton.IBenRobotSDK.interfaces.ISerialCallBack;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.ToastUtils;

import net.posprinter.posprinterface.IMyBinder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSendTestEdit = null;
    private TextView mResultText = null;
    private TextView mStatusText = null;
    private IMyBinder binder = null;
    private final static int period = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
//        initPrint(0);
//        Log.e("---", getProgramNameByPackageName(this, "com.qihancloud.setting"));
//        Log.e("---", getProgramNameByPackageName(this, "com.qihancloud.contact"));
//        Log.e("---", getProgramNameByPackageName(this, "com.qihancloud.update"));
    }

    public static String getProgramNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(pm.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return name;
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
        // IBenChatSDK.getInstance().initIMSDK(this);
        IBenTTSUtil.getInstance().init(this);
        IBenRecordUtil.getInstance().init(this);
        IBenSerialUtil.getInstance().setCallBack(new ISerialCallBack() {
            @Override
            public void onReadData(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResultText.setText(result);
                    }
                });
            }
        });
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
                break;
            case R.id.mRecordTestBtn:
                IBenRecordUtil.getInstance().startRecognize();
                break;
            case R.id.mTranslateTestBtn:
                IBenTranslateSDK.getInstance().translate(msg, "英文", "中文");
                break;
            case R.id.mPrintTestBtn:
//                List<byte[]> mList = PrintBuilder.printContent(msg, PrintConstants.PRINT_CENTER, PrintConstants.FONT_SIZEE_0, 0);
//                // PrintManager.getInstance().BluePrint(binder, mList);
//                PrintManager.getInstance().USBPrint(mList);
                Log.e("---", getProgramNameByPackageName(MainActivity.this, msg));
                break;
            case R.id.mLeftUp:
                break;
            case R.id.mLeftDown:
                break;
            case R.id.mRightUp:
                break;
            case R.id.mRightDown:
                break;
            case R.id.mHeadLeft:
                break;
            case R.id.mHeadMiddle:
                break;
            case R.id.mHeadRight:
                break;
            case R.id.mDancingTestBtn:
                Observable.interval(0, 48500, TimeUnit.MILLISECONDS)
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                dancingTest();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
                break;
            case R.id.mSerialPortBtn:
                IBenSerialUtil.getInstance().sendData(msg);
                break;
        }
//        mSendTestEdit.setText("");
    }

    /**
     * 跳舞测试
     */
    private void dancingTest() {
        try {
            // 第一组动作
//            PwmMotor.getInstance().leftArmUp();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            Thread.sleep(period);
//            PwmMotor.getInstance().rightArmUp();
//            Thread.sleep(period);
//            PwmMotor.getInstance().rightArmDown();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Left30();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Right30();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Right30();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Left30();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            // 第二组动作
//            PwmMotor.getInstance().leftArmUp45();
//            PwmMotor.getInstance().rightArmUp45();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().rightArmDown();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmUp();
//            PwmMotor.getInstance().rightArmUp();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().rightArmDown();
//            Thread.sleep(period);
//            // 第三组动作
//            PwmMotor.getInstance().leftArmUp60();
//            PwmMotor.getInstance().head2Left();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().rightArmUp60();
//            PwmMotor.getInstance().head2Right();
//            Thread.sleep(period);
//            PwmMotor.getInstance().rightArmDown();
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            // 第四组动作
//            PwmMotor.getInstance().rightArmUp60();
//            PwmMotor.getInstance().head2Right();
//            Thread.sleep(period);
//            PwmMotor.getInstance().rightArmDown();
//            PwmMotor.getInstance().leftArmUp60();
//            PwmMotor.getInstance().head2Left();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            // 第五组动作
//            PwmMotor.getInstance().leftArmUp();
//            PwmMotor.getInstance().rightArmUp();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().rightArmDown();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmUp();
//            PwmMotor.getInstance().rightArmUp();
//            Thread.sleep(period);
//            PwmMotor.getInstance().leftArmDown();
//            PwmMotor.getInstance().rightArmDown();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Left();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Left();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Right();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Right();
//            Thread.sleep(period);
//            PwmMotor.getInstance().head2Middle();
//            Thread.sleep(period);
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 0) {
            ToastUtils.showShort("检测到键值>>>" + (event.getScanCode() == 256 ? 0 : 1));
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 0) {
        }
        return super.onKeyUp(keyCode, event);
    }
}
