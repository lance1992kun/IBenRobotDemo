package com.samton.IBenRobotSDK.core;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.samton.IBenRobotSDK.data.Constants;
import com.samton.IBenRobotSDK.utils.FileIOUtils;
import com.samton.IBenRobotSDK.utils.FileUtils;
import com.samton.IBenRobotSDK.utils.ImageUtils;
import com.samton.IBenRobotSDK.utils.LogUtils;
import com.slamtec.slamware.SlamwareCorePlatform;
import com.slamtec.slamware.action.ActionStatus;
import com.slamtec.slamware.action.IMoveAction;
import com.slamtec.slamware.action.MoveDirection;
import com.slamtec.slamware.geometry.PointF;
import com.slamtec.slamware.geometry.Size;
import com.slamtec.slamware.robot.Location;
import com.slamtec.slamware.robot.Map;
import com.slamtec.slamware.robot.MapKind;
import com.slamtec.slamware.robot.MapType;
import com.slamtec.slamware.robot.Rotation;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     @author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/10/18
 *     desc   : 机器人移动SDK
 *     version: 1.0
 * </pre>
 */

public final class IBenMoveSDK {
    /**
     * 单例
     */
    private static IBenMoveSDK instance = null;
    /**
     * 思岚SDK平台对象
     */
    private SlamwareCorePlatform mRobotPlatform;
    /**
     * 动作接口
     */
    private IMoveAction moveAction;
    /**
     * 是否已经连接机器人
     */
    private boolean isConnected = false;
    /**
     * 电池电量信息
     */
    private String mBatteryInfo = null;
    /**
     * 持续移动的计时器
     */
    private Disposable mDisposable;
    /**
     * 机器人状态回调接口
     */
    private ConnectCallBack mCallBack;
    /**
     * 记录IP
     */
    private String mIp = "";
    /**
     * 记录端口
     */
    private int mPort = 0;

    /**
     * 机器人连接回调
     */
    public interface ConnectCallBack {
        /**
         * 连接成功
         */
        void onConnectSuccess();

        /**
         * 连接失败
         */
        void onConnectFailed();
    }

    /**
     * 保存地图回调
     */
    public interface MapCallBack {
        /**
         * 保存成功
         */
        void onSuccess();

        /**
         * 保存失败
         */
        void onFailed();
    }

    /**
     * 动作回调
     */
    public interface MoveCallBack {
        /**
         * 机器人状态变更
         *
         * @param status 状态值(思岚ActionStatus枚举)
         */
        void onStateChange(ActionStatus status);
    }

    /**
     * 电量信息回调
     */
    public interface GetBatteryCallBack {
        /**
         * 获取电量成功
         *
         * @param msg 电量信息
         */
        void onSuccess(String msg);

        /**
         * 获取电量失败
         */
        void onFailed();
    }

    /**
     * 获取机器人移动SDK单例
     *
     * @return 机器人移动SDK单例
     */
    public static IBenMoveSDK getInstance() {
        if (instance == null) {
            synchronized (IBenMoveSDK.class) {
                if (instance == null) {
                    instance = new IBenMoveSDK();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造
     */
    private IBenMoveSDK() {

    }

    /**
     * 唤醒雷达
     */
    public void wakeUp() {
        mRobotPlatform.wakeUp();
    }

    /**
     * 回充电桩
     */
    public void goHome() {
        mRobotPlatform.goHome();
    }

    /**
     * 请求失败
     *
     * @param e 异常信息
     */
    private void onRequestError(Throwable e) {
        cancelTimedAction();
        LogUtils.e(e.getMessage());
        synchronized (this) {
            mRobotPlatform = null;
            isConnected = false;
        }
        mCallBack.onConnectFailed();
    }

    /**
     * 获取机器人连接状态
     *
     * @return 机器人连接状态
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * 连接机器人
     *
     * @param ip       IP
     * @param port     端口
     * @param callBack 连接回调
     */
    public void connectRobot(final String ip, final int port, @NonNull final ConnectCallBack callBack) {
        mCallBack = callBack;
        mIp = ip;
        mPort = port;
        // 判断ip和端口是否合法
        if (mIp == null || mIp.isEmpty() || mPort <= 0 || mPort > 65535) {
            mCallBack.onConnectFailed();
        }
        // 合法的话连接机器人
        else {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        // 设置机器人连接监听
                        mRobotPlatform = SlamwareCorePlatform.connect(ip, port);
                        // 获取机器人电量
                        LogUtils.e("当前机器人电量>>>" + mRobotPlatform.getBatteryPercentage());
                        // LogUtils.e("当前机器人固件版本>>>" + mRobotPlatform.getFirmwareUpdateInfo().getCurrent());
                        // 发射命令-告知观察者连接成功
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        isConnected = true;
                        mCallBack.onConnectSuccess();
                    } else {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        }

    }

    /**
     * 重连机器人
     */
    public void reconnect() {
        connectRobot(mIp, mPort, mCallBack);
    }

    /**
     * 断开机器人连接
     */
    public void disconnectRobot() {
        // 判断机器人控制SDK是否为空
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        mRobotPlatform.disconnect();
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 设置是否开启地图更新
     *
     * @param isUpdate 是或者否
     */
    public void setMapUpdate(boolean isUpdate) {
        if (mRobotPlatform != null) {
            mRobotPlatform.setMapUpdate(isUpdate);
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 清楚地图
     */
    public void removeMap(){
        if (mRobotPlatform != null) {
            mRobotPlatform.clearMap();
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 获取电量百分比
     *
     * @return 电量信息
     */
    public void getBatteryInfo(final GetBatteryCallBack callBack) {
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        int percent = mRobotPlatform.getBatteryPercentage();
                        boolean isCharging = mRobotPlatform.getBatteryIsCharging();
                        jsonObject.put("batteryPercent", percent);
                        jsonObject.put("isCharging", isCharging);
                        mBatteryInfo = jsonObject.toString();
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                        callBack.onFailed();
                    } else {
                        callBack.onSuccess(mBatteryInfo);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
            callBack.onFailed();
            mBatteryInfo = "";
        }
    }

    /**
     * 根据方向进行移动
     *
     * @param direction 方向
     */
    public void moveByDirection(MoveDirection direction) {
        cancelTimedAction();
        final MoveDirection moveDirection = direction;
        if (moveDirection != null && mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        mRobotPlatform.moveBy(moveDirection);
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }

    }

    /**
     * 根据方向进行移动和间隔持续移动
     *
     * @param direction 方向
     * @param period    间隔
     */
    public void moveByDirection(MoveDirection direction, long period) {
        // 首先清除掉定时器
        cancelTimedAction();
        final MoveDirection moveDirection = direction;
        if (moveDirection != null && mRobotPlatform != null) {
            mDisposable = Observable.interval(0, period, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.newThread()).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                            try {
                                mRobotPlatform.moveBy(moveDirection);
                            } catch (Throwable throwable) {
                                onRequestError(throwable);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            onRequestError(throwable);
                        }
                    });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 旋转机器人
     *
     * @param angle 需要旋转的角度
     */
    public void rotate(double angle) {
        float tempAngle = (float) (angle / 180 * Math.PI);
        final Rotation rotation = new Rotation(tempAngle);
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        mRobotPlatform.rotate(rotation);
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 停止所有动作
     */
    public void cancelAllActions() {
        cancelTimedAction();
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    IMoveAction moveAction = mRobotPlatform.getCurrentAction();
                    try {
                        if (moveAction != null) {
                            moveAction.cancel();
                        }
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 获取当前点坐标
     */
    public Location getLocation() {
        if (mRobotPlatform != null) {
            return mRobotPlatform.getLocation();
        } else {
            onRequestError(new Exception("机器人连接失败"));
            return null;
        }

    }

    /**
     * 行走到指定点
     *
     * @param location 定点对象
     */
    public void go2Location(final Location location, final MoveCallBack callBack) {
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        moveAction = mRobotPlatform.moveTo(location);
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    } else {
                        while (true) {
                            if (moveAction != null) {
                                ActionStatus status = moveAction.getStatus();
                                if (status.equals(ActionStatus.FINISHED) || status.equals(ActionStatus.STOPPED)
                                        || status.equals(ActionStatus.ERROR)) {
                                    callBack.onStateChange(status);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 清除所有虚拟墙
     */
    public void clearAllWalls() {
        if (mRobotPlatform != null) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                    try {
                        mRobotPlatform.clearWalls();
                        e.onNext(true);
                    } catch (Throwable throwable) {
                        e.onNext(false);
                    }
                }
            }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (!aBoolean) {
                        onRequestError(new Exception("机器人连接失败"));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    onRequestError(throwable);
                }
            });
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 保存地图
     *
     * @param mapName  要保存地图的名字
     * @param callBack 回调函数
     */
    public void saveMap(final String mapName, final MapCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                try {
                    // 获取底盘的Map对象
                    Map map = getMap();
                    if (map != null) {
                        // 判断目录是否存在不存在的话创建
                        FileUtils.createOrExistsDir(Constants.MAP_PATH);
                        // 文件输出流对象
                        FileOutputStream fos = new FileOutputStream(Constants.MAP_PATH + "/" + mapName);
                        // 对象输出流(写入)
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        // 写入Origin对象
                        oos.writeFloat(map.getOrigin().getX());
                        oos.writeFloat(map.getOrigin().getY());
                        // 写入Dimension对象
                        oos.writeInt(map.getDimension().getWidth());
                        oos.writeInt(map.getDimension().getHeight());
                        // 写入Resolution对象
                        oos.writeFloat(map.getResolution().getX());
                        oos.writeFloat(map.getResolution().getY());
                        // 写入时间戳对象
                        oos.writeLong(map.getTimestamp());
                        // 写入真实地图对象
                        oos.writeObject(map.getData());
                        // 关闭流并且刷新操作
                        oos.close();
                        // 生成缩略图
                        FileUtils.createOrExistsFile(Constants.MAP_PATH_THUMB + "/" + mapName);
                        // 写入BMP图像
                        int width = map.getDimension().getWidth();
                        int height = map.getDimension().getHeight();
                        Bitmap bitmap = createImage(map.getData(), width, height);
                        byte[] bytes = ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG);
                        FileIOUtils.writeFileFromBytesByStream(FileUtils.getFileByPath(Constants.MAP_PATH_THUMB + "/" + mapName),
                                bytes);
                    } else {
                        e.onNext(false);
                    }
                    e.onNext(true);
                } catch (Throwable throwable) {
                    e.onNext(false);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    callBack.onSuccess();
                } else {
                    callBack.onFailed();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                callBack.onFailed();
            }
        });
    }

    /**
     * 根据地图名字加载地图
     *
     * @param mapName  地图名字
     * @param callBack 回调函数
     */
    public void loadMap(final String mapName, final MapCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                try {
                    // 获取地图文件
                    File file = new File(Constants.MAP_PATH + "/" + mapName);
                    // 地图文件不存在直接返回
                    if (!FileUtils.isFileExists(file)) {
                        e.onNext(false);
                    }
                    // 地图文件存在才继续做事情
                    else {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
                        // 生成Origin对象
                        float ox = ois.readFloat();
                        float oy = ois.readFloat();
                        PointF origin = new PointF(ox, oy);
                        // 生成Dimension对象
                        int width = ois.readInt();
                        int height = ois.readInt();
                        Size size = new Size(width, height);
                        // 生成Resolution对象
                        float rx = ois.readFloat();
                        float ry = ois.readFloat();
                        PointF resolution = new PointF(rx, ry);
                        // 赋值时间戳
                        long timeStamp = ois.readLong();
                        // 生成二进制数组
                        byte[] data = (byte[]) ois.readObject();
                        // 生成地图对象
                        Map map = new Map(origin, size, resolution, timeStamp, data);
                        // 设置地图
                        setMap(map);
                        // 关闭流
                        ois.close();
                        e.onNext(true);
                    }
                } catch (Throwable throwable) {
                    e.onNext(false);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    callBack.onSuccess();
                } else {
                    callBack.onFailed();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                callBack.onFailed();
            }
        });
    }

    /**
     * 获取地图
     *
     * @return 返回地图
     */
    private Map getMap() {
        if (mRobotPlatform != null) {
            // 地图类型为8位位图
            MapType mapType = MapType.BITMAP_8BIT;
            // 地图种类为扫描建图
            MapKind mapKind = MapKind.EXPLORE_MAP;
            return mRobotPlatform.getMap(mapType, mapKind, mRobotPlatform.getKnownArea(mapType));
        } else {
            onRequestError(new Exception("机器人连接失败"));
            return null;
        }
    }

    /**
     * 更新地图
     *
     * @param map 需要设置的地图
     */
    private void setMap(Map map) {
        if (mRobotPlatform != null) {
            mRobotPlatform.setMap(map);
        } else {
            onRequestError(new Exception("机器人连接失败"));
        }
    }

    /**
     * 清空计时器
     */
    private void cancelTimedAction() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    /**
     * 创建bitmap对象
     *
     * @param buffer 数据流
     * @param width  宽
     * @param height 高
     * @return 创建好的BitMap对象
     */
    @SuppressWarnings("NumericOverflow")
    private Bitmap createImage(byte[] buffer, int width, int height) {
        int[] rawData = new int[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            int temp = 0x7f + buffer[i];
            rawData[i] = Color.rgb(temp, temp, temp);
        }
//        Bitmap result = Bitmap.createBitmap(rawData, width, height, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(result);
//        Bitmap temp = ImageUtils.getBitmap(android.R.mipmap.sym_def_app_icon);
//        canvas.drawBitmap(temp, 0, 0, null);
//        canvas.save();
//        canvas.restore();
//        return result;
        return Bitmap.createBitmap(rawData, width, height, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true);
    }
}
