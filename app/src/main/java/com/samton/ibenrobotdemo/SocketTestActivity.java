package com.samton.ibenrobotdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.samton.IBenRobotSDK.core.IBenMoveSDK;
import com.samton.IBenRobotSDK.core.IBenTTSUtil;
import com.samton.IBenRobotSDK.data.Constants;
import com.samton.IBenRobotSDK.interfaces.IBenTTSCallBack;
import com.samton.IBenRobotSDK.utils.CacheUtils;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.NetworkUtils;
import com.samton.ibenrobotdemo.data.UploadMapBean;
import com.samton.ibenrobotdemo.net.APIService;
import com.samton.ibenrobotdemo.net.HttpUtil;
import com.samton.pwmmotor.PwmMotor;
import com.slamtec.slamware.action.ActionStatus;
import com.slamtec.slamware.action.MoveDirection;
import com.slamtec.slamware.robot.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class SocketTestActivity extends AppCompatActivity implements RobotSocketServer.MessageCallBack {

    private RobotSocketServer mServer = new RobotSocketServer(20009);
    private TextView mIpAddress;
    private TextView mReceiveMsg;
    private IBenMoveSDK moveSDK;
    private PwmMotor mPwmMotor;
    private EditText mIpEdit;
    private String mLocations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();
        initData();
    }

    private void initView() {
        mIpAddress = (TextView) findViewById(R.id.mIpAddress);
        mReceiveMsg = (TextView) findViewById(R.id.mReceiveMsg);
        mIpEdit = (EditText) findViewById(R.id.mIpEdit);
        findViewById(R.id.mConnectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectRobot();
            }
        });
    }

    private void connectRobot() {
        String ip = mIpEdit.getText().toString().trim();
        moveSDK.connectRobot(ip, 1445, new IBenMoveSDK.ConnectCallBack() {
            @Override
            public void onConnectSuccess() {
                initSocket();
            }

            @Override
            public void onConnectFailed() {
                Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                moveSDK.reconnect();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
            }
        });
    }

    private void initData() {
        IBenTTSUtil.getInstance().init(this);
        moveSDK = IBenMoveSDK.getInstance();
        mPwmMotor = PwmMotor.getInstance();
        mPwmMotor.openDevices();
    }

    private void initSocket() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIpAddress.setText("本机IP>>>" + NetworkUtils.getIPAddress(true) + "端口>>>20009");
            }
        });
        mServer.startServer();
        mServer.setCallBack(this);
    }

    @Override
    public void onReceive(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mReceiveMsg.setText(message);
            }
        });
        try {
            JSONObject jsonObject = new JSONObject(message);
            // 指令类型
            String command = jsonObject.optString("command");
            // 指令类型的子类型
            String type = jsonObject.optString("type");
            // 子类型的内容
            String content = jsonObject.optString("content");
            switch (command) {
                // 语音指令
                case "voiceMessage":
                    handleVoiceMessage(content);
                    break;
                // 移动指令
                case "moveMessage":
                    handleMoveMessage(type, content);
                    break;
                // 表情指令
                case "imageMessage":
                    handleImageMessage(content);
                    break;
                // 电源指令
                case "batteryMessage":
                    handleBatteryMessage(content);
                    break;
                // 地图指令
                case "mapMessage":
                    handleMapMessage(type, content);
                    break;
                // 肢体指令
                case "limbMessage":
                    handleLimbMessage(type, content);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理语音信息
     *
     * @param content 要说的话
     */
    private void handleVoiceMessage(String content) {
        IBenTTSUtil.getInstance().startSpeaking(content, new IBenTTSCallBack() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onCompleted(SpeechError error) {

            }
        });
    }

    /**
     * 处理移动信息
     *
     * @param type    运动类型
     * @param content 运动指令
     */
    private void handleMoveMessage(String type, String content) {
        switch (type) {
            // 移动指令
            case "move":
                switch (content) {
                    case "left":
                        moveSDK.moveByDirection(MoveDirection.TURN_LEFT, 300);
                        break;
                    case "right":
                        moveSDK.moveByDirection(MoveDirection.TURN_RIGHT, 300);
                        break;
                    case "forward":
                        moveSDK.moveByDirection(MoveDirection.FORWARD, 300);
                        break;
                    case "backward":
                        moveSDK.moveByDirection(MoveDirection.BACKWARD, 300);
                        break;
                    case "stop":
                        moveSDK.cancelAllActions();
                        break;
                    default:
                        break;
                }
                break;
            // 转动角度指令
            case "turn":
                moveSDK.rotate(Double.valueOf(content));
                break;
            default:
                break;
        }
    }

    /**
     * 处理面部信息
     *
     * @param content 要切换的表情名字
     */
    private void handleImageMessage(String content) {

    }

    /**
     * 处理电源信息
     *
     * @param content 内容
     */
    private void handleBatteryMessage(String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command", "return");
            jsonObject.put("type", "batteryMessage");
            jsonObject.put("content", moveSDK.getBatteryInfo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mServer.sendMessage(jsonObject.toString());
    }

    /**
     * 处理地图信息
     *
     * @param type    类型
     * @param content 指令
     */
    private void handleMapMessage(String type, final String content) {
        switch (type) {
            // 获取地图
            case "getMap":
                moveSDK.saveMap(content, new IBenMoveSDK.MapCallBack() {
                    @Override
                    public void onSuccess() {
                        File file = new File(Constants.MAP_PATH_THUMB + "/" + content);
                        mServer.sendMap(file);
                    }

                    @Override
                    public void onFailed() {
                        mServer.sendMessage("获取地图失败");
                    }
                });
                break;
            // 保存地图
            case "saveMap":
                moveSDK.saveMap(content, new IBenMoveSDK.MapCallBack() {
                    @Override
                    public void onSuccess() {
                        File file = new File(Constants.MAP_PATH + "/" + content);
                        // 定点信息
                        String savedLocations = CacheUtils.getInstance().getString(content);
                        if (TextUtils.isEmpty(savedLocations)) {
                            savedLocations = "";
                        }
                        // 地图文件体
                        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
                        // 地图缩略图文件
                        RequestBody imageFileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part imgFilePart = MultipartBody.Part.createFormData("imgFile", file.getName(), imageFileRequestBody);
                        HttpUtil.getInstance().create(APIService.class)
                                .addScene(RequestBody.create(null, "9aabd5edb430460c9582769041bd2539"),
                                        RequestBody.create(null, content),
                                        RequestBody.create(null, savedLocations),
                                        filePart, imgFilePart).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<UploadMapBean>() {
                                    @Override
                                    public void accept(UploadMapBean uploadMapBean) throws Exception {
                                        LogUtils.e("Msg>>>" + uploadMapBean.getMsg());
                                        LogUtils.e("Rs>>>" + uploadMapBean.getRs());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        LogUtils.e(throwable.getMessage());
                                    }
                                });
                        mServer.sendMessage("保存地图成功");
                    }

                    @Override
                    public void onFailed() {
                        mServer.sendMessage("保存地图失败");
                    }
                });
                // TODO 清空保存地址缓存文件，写入缓存
//                CacheUtils.getInstance().put(content, mLocations);
//                mLocations = null;
                break;
            // 获取当前点
            case "getLocation":
                Location currentLocation = moveSDK.getLocation();
                if (currentLocation != null) {
                    try {
                        String location = currentLocation.getX() + "," + currentLocation.getY() + "," + currentLocation.getZ();
                        JSONObject object;
                        if (TextUtils.isEmpty(mLocations)) {
                            object = new JSONObject();
                        } else {
                            object = new JSONObject(mLocations);
                        }
                        object.put(content, location);
                        mLocations = object.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mServer.sendMessage("设置点成功");
                } else {
                    mServer.sendMessage("设置点失败");
                }
                break;
            // 回充电桩
            case "goHome":
                moveSDK.goHome();
                break;
            // 停止地图刷新
            case "setMapUpdate":
                moveSDK.setMapUpdate(Boolean.parseBoolean(content));
                break;
            // 行走到指定点
            case "navigation":
                String[] points = content.split(",");
                Location destination = new Location(Float.parseFloat(points[0]), Float.parseFloat(points[1]), Float.parseFloat(points[2]));
                moveSDK.go2Location(destination, new IBenMoveSDK.MoveCallBack() {
                    @Override
                    public void onStateChange(ActionStatus status) {
                        if (status.equals(ActionStatus.FINISHED)) {
                            mServer.sendMessage("到达指定位置");
                        } else {
                            mServer.sendMessage("无法到达指定位置");
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 处理肢体信息
     *
     * @param type    类型
     * @param content 指令
     */
    private void handleLimbMessage(String type, String content) {
        int index = 0;
        switch (type) {
            // 左手
            case "leftArm":
                index = 1;
                break;
            // 右手
            case "rightArm":
                index = 2;
                break;
            // 头部
            case "head":
                index = 3;
                break;
            default:
                break;
        }
        switch (content) {
            case "up":
                if (index == 1) {
                    mPwmMotor.leftArmUp();
                } else if (index == 2) {
                    mPwmMotor.rightArmUp();
                }
                break;
            case "down":
                if (index == 1) {
                    mPwmMotor.leftArmDown();
                } else if (index == 2) {
                    mPwmMotor.rightArmDown();
                }
                break;
            case "left":
                mPwmMotor.head2Left();
                break;
            case "middle":
                mPwmMotor.head2Middle();
                break;
            case "right":
                mPwmMotor.head2Right();
                break;
        }
    }
}
