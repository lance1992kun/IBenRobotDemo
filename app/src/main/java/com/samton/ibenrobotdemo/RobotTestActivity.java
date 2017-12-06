package com.samton.ibenrobotdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.samton.IBenRobotSDK.core.IBenMoveSDK;
import com.samton.IBenRobotSDK.core.MainSDK;
import com.samton.IBenRobotSDK.utils.ToastUtils;
import com.slamtec.slamware.action.MoveDirection;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/26
 *     desc   : 机器人连接测试
 *     version: 1.0
 * </pre>
 */

public class RobotTestActivity extends AppCompatActivity implements View.OnClickListener, IBenMoveSDK.ConnectCallBack {

    private IBenMoveSDK moveSDK;
    private EditText mIPEdit;
    private EditText mAngleEdit;
    /**
     * 用来显示联网的对话框
     */
    private ProgressDialog mConnectingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_test);
        initView();
        initData();

//        HttpUtil.getInstance().create(APIService.class)
//                .initRobot("bdcae7bbee864ec99ed04cf7bd2c727c")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<InitBean>() {
//                    @Override
//                    public void accept(InitBean initBean) throws Exception {
//                        String msg = "";
//                        msg += "机器人名字>>>" + initBean.getRobotName();
//                        msg += "\n发音人>>>" + initBean.getVoiceTag();
//                        msg += "\n花痴表情>>>" + initBean.getExpressionList().getAnthomaniac();
//                        msg += "\n唤醒表情>>>" + initBean.getExpressionList().getAwaken();
//                        msg += "\n开心表情>>>" + initBean.getExpressionList().getHappy();
//                        msg += "\n悲伤表情>>>" + initBean.getExpressionList().getSad();
//                        msg += "\n微笑表情>>>" + initBean.getExpressionList().getSmile();
//                        msg += "\n说话表情>>>" + initBean.getExpressionList().getSpeak();
//                        msg += "\ntoken>>>" + initBean.get_token_iben();
//                        InitBean.MapBean mapBean = initBean.getCurrentMap();
//                        msg += "\n当前地图地址>>>" + mapBean.getFile();
//                        msg += "\n当前地图名字>>>" + mapBean.getMapName();
//                        for (int i = 0; i < mapBean.getPositionPoints().size(); i++) {
//                            msg += "\n当前点名字>>>" + mapBean.getPositionPoints().get(i).getName();
//                            msg += "\n当前点坐标>>>" + mapBean.getPositionPoints().get(i).getLocation();
//                        }
//                        List<InitBean.MapBean> mapBeanList = initBean.getMaps();
//                        msg += "\n地图有>>>" + mapBeanList.size() + "个";
//                        for (int i = 0; i < mapBeanList.size(); i++) {
//                            InitBean.MapBean mapBean2 = mapBeanList.get(i);
//                            msg += "\n第" + i + "个";
//                            msg += "\n地图名字>>>" + mapBean2.getMapName();
//                            msg += "\n地图地址" + mapBean2.getFile();
//                            for (int j = 0; j < mapBean2.getPositionPoints().size(); j++) {
//                                msg += "\n当前点名字>>>" + mapBean2.getPositionPoints().get(j).getName();
//                                msg += "\n当前点坐标>>>" + mapBean2.getPositionPoints().get(j).getLocation();
//                            }
//                        }
//                        msg += "\n机器人工作模式>>>" + initBean.getRobotMod();
//                        LogUtils.e("\n初始化成功" + msg);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        LogUtils.e(throwable.getMessage());
//                    }
//                });
    }

    private void initView() {
        mIPEdit = (EditText) findViewById(R.id.mIpEdit);
//        mPortEdit = (EditText) findViewById(R.id.mPortEdit);
        mAngleEdit = (EditText) findViewById(R.id.mAngleEdit);

        findViewById(R.id.mConnectBtn).setOnClickListener(this);
        findViewById(R.id.mLeftBtn).setOnClickListener(this);
        findViewById(R.id.mRightBtn).setOnClickListener(this);
        findViewById(R.id.mBackBtn).setOnClickListener(this);
        findViewById(R.id.mForwardBtn).setOnClickListener(this);
        findViewById(R.id.mRotateBtn).setOnClickListener(this);
        findViewById(R.id.mStopBtn).setOnClickListener(this);
        findViewById(R.id.mSaveMapBtn).setOnClickListener(this);
        findViewById(R.id.mLoadMapBtn).setOnClickListener(this);
        findViewById(R.id.mGoHomeBtn).setOnClickListener(this);
    }

    private void initData() {
        MainSDK.init(getApplication());
        moveSDK = IBenMoveSDK.getInstance();
    }

    private void stop() {
        moveSDK.cancelAllActions();
    }

    @Override
    public void onClick(View v) {
        if (!moveSDK.isConnected() && v.getId() != R.id.mConnectBtn) {
            ToastUtils.showShort("机器人尚未连接!请先连接机器人!");
            return;
        }
        switch (v.getId()) {
            case R.id.mStopBtn:
                stop();
                break;
            case R.id.mConnectBtn:
                if (moveSDK.isConnected()) {
                    ToastUtils.showShort("机器人已经连接！");
                } else {
                    String ip = mIPEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(ip)) {
                        ToastUtils.showShort("请输入IP");
                        return;
                    } else {
                        moveSDK.connectRobot(ip, 1445, this);
                        showConnectingDialog("机器人连接中……");
                    }
                }
                break;
            case R.id.mLeftBtn:
                moveSDK.moveByDirection(MoveDirection.TURN_LEFT, 100);
                break;
            case R.id.mRightBtn:
                moveSDK.moveByDirection(MoveDirection.TURN_RIGHT, 100);
                break;
            case R.id.mBackBtn:
                moveSDK.moveByDirection(MoveDirection.BACKWARD, 100);
                break;
            case R.id.mForwardBtn:
                moveSDK.moveByDirection(MoveDirection.FORWARD, 100);
                break;
            case R.id.mRotateBtn:
                String tempAngle = mAngleEdit.getText().toString().trim();
                if (TextUtils.isEmpty(tempAngle)) {
                    ToastUtils.showShort("请输入角度");
                    return;
                } else {
                    try {
                        double angle = Double.valueOf(tempAngle);
                        moveSDK.rotate(angle);
                    } catch (NumberFormatException e) {
                        ToastUtils.showShort("请输入正确的角度");
                    }
                }
                break;
            case R.id.mSaveMapBtn:
                showConnectingDialog("正在保存地图……");
                moveSDK.saveMap("测试1", new IBenMoveSDK.MapCallBack() {
                    @Override
                    public void onSuccess() {
                        hideConnectingDialog();
                        ToastUtils.showShort("保存成功");
                    }

                    @Override
                    public void onFailed() {
                        hideConnectingDialog();
                        ToastUtils.showShort("保存失败");
                    }
                });
                break;
            case R.id.mLoadMapBtn:
                showConnectingDialog("正在加载地图……");
                moveSDK.loadMap("测试1", new IBenMoveSDK.MapCallBack() {
                    @Override
                    public void onSuccess() {
                        hideConnectingDialog();
                        ToastUtils.showShort("加载成功");
                    }

                    @Override
                    public void onFailed() {
                        hideConnectingDialog();
                        ToastUtils.showShort("加载失败");
                    }
                });
                break;
            case R.id.mGoHomeBtn:
                moveSDK.goHome();
                break;
        }
    }

    @Override
    public void onConnectSuccess() {
        hideConnectingDialog();
        // 连接成功
        ToastUtils.showShort("机器人连接成功");
    }

    @Override
    public void onConnectFailed() {
        // 连接失败
        ToastUtils.showShort("机器人连接失败");
        hideConnectingDialog();
    }

    /**
     * 显示对话框
     *
     * @param msg 要显示的话
     */
    public void showConnectingDialog(String msg) {
        // 没有对话框的话显示连接对话框
        if (mConnectingDialog == null) {
            mConnectingDialog = new ProgressDialog(this);
            mConnectingDialog.setMessage(msg);
            mConnectingDialog.setCancelable(false);
            mConnectingDialog.setCanceledOnTouchOutside(false);
            mConnectingDialog.show();
        }
    }

    /**
     * 隐藏连接对话框
     */
    public void hideConnectingDialog() {
        if (mConnectingDialog != null) {
            mConnectingDialog.dismiss();
            mConnectingDialog = null;
        }
    }
}
