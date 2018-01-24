package com.samton.ibenrobotdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.samton.IBenRobotSDK.core.IBenPrintSDK;
import com.samton.IBenRobotSDK.core.IBenRecordUtil;
import com.samton.IBenRobotSDK.core.IBenSerialUtil;
import com.samton.IBenRobotSDK.core.IBenTTSUtil;
import com.samton.IBenRobotSDK.core.MainSDK;
import com.samton.IBenRobotSDK.interfaces.ISerialCallBack;
import com.samton.IBenRobotSDK.utils.ToastUtils;
import com.samton.ibenrobotdemo.data.SerialMsgHelper;

import net.posprinter.utils.DataForSendToPrinterPos80;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSendTestEdit = null;
    private TextView mResultText = null;

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

        findViewById(R.id.mLeftUp).setOnClickListener(this);
        findViewById(R.id.mLeftDown).setOnClickListener(this);
        findViewById(R.id.mRightUp).setOnClickListener(this);
        findViewById(R.id.mRightDown).setOnClickListener(this);
        findViewById(R.id.mHeadLeft).setOnClickListener(this);
        findViewById(R.id.mHeadMiddle).setOnClickListener(this);
        findViewById(R.id.mHeadRight).setOnClickListener(this);
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
        IBenPrintSDK.getInstance().initPrinter(this);
    }

    private List<byte[]> printContentFactory() {
        List<byte[]> bytes = new ArrayList<>();
        bytes.addAll(PrintBuilder.printContent("北京市*****有限公司", 1, PrintConstants.FONT_SIZEE_0, 3));
        bytes.addAll(PrintBuilder.printContent("退卡申请凭证", 1, PrintConstants.FONT_SIZEE_1, 3));
        bytes.addAll(PrintBuilder.printContent("         商户号       00034000001", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         商户名称     ***公司", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         网点编号     000400700010", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         网点名称     ***退卡4", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         用户卡号     1000751087394759", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         业务类型     坏卡【00-01】", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         中心流水号   195024758", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         交易时间     2016-06-25 09:10:06", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         重打印时间   2016-06-25 09:10:28", 0, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("         操作员ID     000001", 0, PrintConstants.FONT_SIZEE_0, 3));
        bytes.addAll(PrintBuilder.printContent("退卡/资需出示，请妥善保管", 1, PrintConstants.FONT_SIZEE_0, 1));
        bytes.addAll(PrintBuilder.printContent("重打印凭证/DHUOGJKDNWD", 1, PrintConstants.FONT_SIZEE_0, 5));
        bytes.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66, 1));
        return bytes;
    }


    @Override
    public void onClick(View v) {
        String msg = mSendTestEdit.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showShort("请输入要发送/说的话");
            return;
        }
        switch (v.getId()) {
            case R.id.mLeftUp:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getArmMsg(SerialMsgHelper.Action.LEFT_ARM_UP));
                break;
            case R.id.mLeftDown:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getArmMsg(SerialMsgHelper.Action.LEFT_ARM_DOWN));
                break;
            case R.id.mRightUp:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getArmMsg(SerialMsgHelper.Action.RIGHT_ARM_UP));
                break;
            case R.id.mRightDown:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getArmMsg(SerialMsgHelper.Action.RIGHT_ARM_DOWN));
                break;
            case R.id.mHeadLeft:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getHeadMsg(SerialMsgHelper.Action.HEAD_LEFT));
                break;
            case R.id.mHeadMiddle:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getHeadMsg(SerialMsgHelper.Action.HEAD_MIDDLE));
                break;
            case R.id.mHeadRight:
                IBenSerialUtil.getInstance().sendData(
                        SerialMsgHelper.getHeadMsg(SerialMsgHelper.Action.HEAD_RIGHT));
                break;
            case R.id.mSerialPortBtn:
                IBenSerialUtil.getInstance().sendData(msg);
                // LogUtils.e("打印机状态--->" + IBenPrintSDK.getInstance().isConnect());
                IBenPrintSDK.getInstance().print(printContentFactory());
                break;
        }
    }

}
