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
import com.samton.IBenRobotSDK.utils.FileUtils;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.samton.IBenRobotSDK.utils.MessageHelper;
import com.samton.IBenRobotSDK.utils.NetworkUtils;
import com.samton.ibenrobotdemo.data.UploadMapBean;
import com.samton.ibenrobotdemo.net.APIService;
import com.samton.ibenrobotdemo.net.HttpUtil;
import com.samton.pwmmotor.PwmMotor;
import com.slamtec.slamware.action.ActionStatus;
import com.slamtec.slamware.action.MoveDirection;
import com.slamtec.slamware.robot.Location;

import org.json.JSONArray;
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
    private JSONArray uploadArray;

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
        moveSDK.getBatteryInfo(new IBenMoveSDK.GetBatteryCallBack() {
            @Override
            public void onSuccess(String msg) {
                mServer.sendMessage(MessageHelper.getBatteryMessage(msg));
            }

            @Override
            public void onFailed() {
                mServer.sendMessage(MessageHelper.getBatteryMessage("未能获取电量信息"));
            }
        });
    }

    /**
     * 处理地图信息
     *
     * @param type    类型
     * @param content 指令
     */
    private void handleMapMessage(String type, final String content) {
        switch (type) {
            // 取消地图
            case "cancelMap":
                if (uploadArray != null) {
                    uploadArray = null;
                }
                mServer.sendMessage(MessageHelper.getMapMessage("cancelMap", "取消地图成功"));
                break;
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
                        mServer.sendMessage(MessageHelper.getMapMessage("getMap", "获取地图失败"));
                    }
                });
                break;
            // 保存地图
            case "saveMap":
                moveSDK.saveMap(content, new IBenMoveSDK.MapCallBack() {
                    @Override
                    public void onSuccess() {
                        File file = new File(Constants.MAP_PATH + "/" + content);
                        File thumbFile = new File(Constants.MAP_PATH_THUMB + "/" + content);
                        // 定点信息
                        String mLocations = uploadArray.toString();
                        if (TextUtils.isEmpty(mLocations)) {
                            mLocations = "[]";
                        }
                        final String finalLocations = mLocations;
                        // 地图文件体
                        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("*/*"), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                                file.getName(), fileRequestBody);
                        // 地图缩略图文件
                        RequestBody imageFileRequestBody = RequestBody.create(MediaType.parse("image/*"), thumbFile);
                        MultipartBody.Part imgFilePart = MultipartBody.Part.createFormData("imgFile",
                                thumbFile.getName(), imageFileRequestBody);
                        HttpUtil.getInstance().create(APIService.class)
                                .addScene(RequestBody.create(null, "9aabd5edb430460c9582769041bd2539"),
                                        RequestBody.create(null, content),
                                        RequestBody.create(null, finalLocations),
                                        filePart, imgFilePart)
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<UploadMapBean>() {
                                    @Override
                                    public void accept(UploadMapBean uploadMapBean) throws Exception {
                                        if (uploadMapBean.getRs() == 1) {
                                            CacheUtils.getInstance().put(content, finalLocations);
                                            uploadArray = null;
                                            mServer.sendMessage(MessageHelper.getMapMessage("saveMap", "保存地图成功"));
                                        } else {
                                            mServer.sendMessage(MessageHelper.getMapMessage("saveMap", "保存地图失败"));
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        LogUtils.e(throwable.getMessage());
                                        mServer.sendMessage(MessageHelper.getMapMessage("saveMap", "保存地图失败"));
                                    }
                                });
                    }

                    @Override
                    public void onFailed() {
                        mServer.sendMessage(MessageHelper.getMapMessage("saveMap", "保存地图失败"));
                    }
                });
                break;
            // 获取当前点
            case "getLocation":
                Location currentLocation = moveSDK.getLocation();
                if (currentLocation != null) {
                    try {
                        String location = currentLocation.getX() + ","
                                + currentLocation.getY() + "," + currentLocation.getZ();
                        JSONObject object = new JSONObject();
                        object.put("name", content);
                        object.put("location", location);
                        if (uploadArray == null) {
                            uploadArray = new JSONArray();
                        }
                        uploadArray.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mServer.sendMessage(MessageHelper.getMapMessage("getLocation", "设置点成功"));
                } else {
                    mServer.sendMessage(MessageHelper.getMapMessage("getLocation", "设置点失败"));
                }
                break;
            // 移除指定点
            case "removeLocation":
                if (removePoint(content)) {
                    mServer.sendMessage(MessageHelper.getMapMessage("removeLocation", "移除点失败"));
                } else {
                    mServer.sendMessage(MessageHelper.getMapMessage("removeLocation", "移除点成功"));
                }
                break;
            // 修改指定点
            case "editLocation":
                if (editPoint(content)) {
                    mServer.sendMessage(MessageHelper.getMapMessage("editLocation", "修改点成功"));
                } else {
                    mServer.sendMessage(MessageHelper.getMapMessage("editLocation", "修改点失败"));
                }
                break;
            // 回充电桩
            case "goHome":
                moveSDK.goHome();
                break;
            // 删除地图
            case "removeMap":
                try {
                    moveSDK.removeMap();
                    File mapFile = new File(Constants.MAP_PATH + "/" + content);
                    if (FileUtils.isFileExists(mapFile)) {
                        FileUtils.deleteFile(mapFile);
                        FileUtils.deleteFile(new File(Constants.MAP_PATH_THUMB + "/" + content));
                        CacheUtils.getInstance().remove(content);
                    }
                    mServer.sendMessage(MessageHelper.getMapMessage("removeMap", "删除地图信息成功"));
                } catch (Throwable throwable) {
                    mServer.sendMessage(MessageHelper.getMapMessage("removeMap", "删除地图信息失败"));
                }
                break;
            // 停止地图刷新
            case "setMapUpdate":
                moveSDK.setMapUpdate(Boolean.parseBoolean(content));
                break;
            // 行走到指定点
            case "navigation":
                String[] points = content.split(",");
                Location destination = new Location(Float.parseFloat(points[0]),
                        Float.parseFloat(points[1]), Float.parseFloat(points[2]));
                moveSDK.go2Location(destination, new IBenMoveSDK.MoveCallBack() {
                    @Override
                    public void onStateChange(ActionStatus status) {
                        if (status.equals(ActionStatus.FINISHED)) {
                            mServer.sendMessage(MessageHelper.getMapMessage("navigation", "到达指定位置"));
                        } else {
                            mServer.sendMessage(MessageHelper.getMapMessage("navigation", "无法到达指定位置"));
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

    /**
     * 根据名字删除点
     *
     * @param content 名字
     * @return 成功是否失败
     */
    private boolean removePoint(String content) {
        boolean isSuccess = false;
        if (uploadArray != null) {
            for (int i = 0; i < uploadArray.length(); i++) {
                JSONObject removeObject = uploadArray.optJSONObject(i);
                if (removeObject.has(content)) {
                    uploadArray.remove(i);
                    isSuccess = true;
                    break;
                }
            }
        }
        return isSuccess;
    }

    /**
     * 根据信息更改
     *
     * @param content 信息
     * @return 成功与否
     */
    private boolean editPoint(String content) {
        boolean isSuccess = false;
        String[] editPoints = content.split(",");
        String oldName = editPoints[0];
        String newName = editPoints[1];
        String oldContent = null;
        int index = -1;
        if (uploadArray != null) {
            for (int i = 0; i < uploadArray.length(); i++) {
                JSONObject removeObject = uploadArray.optJSONObject(i);
                if (removeObject.has(oldName)) {
                    index = i;
                    oldContent = removeObject.optString(oldName);
                    break;
                }
            }
            try {
                JSONObject editObject = new JSONObject();
                editObject.put(newName, oldContent);
                uploadArray.remove(index);
                isSuccess = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
}
