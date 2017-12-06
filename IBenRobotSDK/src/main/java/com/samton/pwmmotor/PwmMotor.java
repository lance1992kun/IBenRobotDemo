package com.samton.pwmmotor;


/**
 * <pre>
 *     @author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/08/15
 *     desc   : 舵机管理器(样机 - 南昌3)
 *     version: 2.0
 * </pre>
 */

public class PwmMotor {
    /**
     * 周期
     */
    private final static int PERIOD_NS = 20 * 1000000;
    /**
     * 左手垂直时间点
     */
    private final static double LEFT_ARM_VERTICAL = 1.45 * 1000000;
    /**
     * 左手水平时间点
     */
    private final static double LEFT_ARM_HORIZONTAL = 2.05 * 1000000;
    /**
     * 右手垂直时间点
     */
    private final static double RIGHT_ARM_VERTICAL = 2.05 * 1000000;
    /**
     * 右手水平时间点
     */
    private final static double RIGHT_ARM_HORIZONTAL = 1.45 * 1000000;
    /**
     * 头部左侧时间点
     */
    private final static double HEAD_LEFT = 1.42 * 1000000;
    /**
     * 头部左侧回中间时间点
     */
    private final static double HEAD_LEFT_2_MIDDLE = 1.62 * 1000000;
    /**
     * 头部右侧时间点
     */
    private final static double HEAD_RIGHT = 1.82 * 1000000;
    /**
     * 头部右侧回中间时间点
     */
    private final static double HEAD_RIGHT_2_MIDDLE = 1.62 * 1000000;
    /**
     * 头部在左侧的常量值
     */
    private final static int POSITION_LEFT = 1;
    /**
     * 头部在中间的常量值
     */
    private final static int POSITION_MIDDLE = 0;
    /**
     * 头部在右侧的常量值
     */
    private final static int POSITION_RIGHT = 2;
    /**
     * 舵机管理器单例对象
     */
    private static PwmMotor instance = new PwmMotor();

    /**
     * 获取舵机管理器单例
     *
     * @return 舵机管理器单例
     */
    public static PwmMotor getInstance() {
        if (instance == null) {
            synchronized (PwmMotor.class) {
                if (instance == null) {
                    instance = new PwmMotor();
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造
     */
    private PwmMotor() {
    }

    /**
     * 打开设备
     */
    public boolean openDevices() {
        return open();
    }

//    /**
//     * 关闭设备
//     */
//    public void closeDevices() {
//        close();
//    }

    /**
     * 左胳膊上抬
     */
    public void leftArmUp() {
        // 调整3号舵机
        config(3, (int) LEFT_ARM_HORIZONTAL, PERIOD_NS);
    }

    /**
     * 左胳膊下降
     */
    public void leftArmDown() {
        // 调整3号舵机
        config(3, (int) LEFT_ARM_VERTICAL, PERIOD_NS);
    }

    /**
     * 右胳膊上抬
     */
    public void rightArmUp() {
        // 调整2号舵机
        config(2, (int) RIGHT_ARM_HORIZONTAL, PERIOD_NS);
    }

    /**
     * 右胳膊下降
     */
    public void rightArmDown() {
        // 调整2号舵机
        config(2, (int) RIGHT_ARM_VERTICAL, PERIOD_NS);
    }

    /**
     * 头部转向左侧
     */
    public void head2Left() {
//        // 判断头部是否在左侧
//        if (SPUtils.getInstance().getInt(ROBOT_HEAD_POSITION, POSITION_MIDDLE) != POSITION_LEFT) {
//            // 修改状态
//            SPUtils.getInstance().put(ROBOT_HEAD_POSITION, POSITION_LEFT);
//            // 调整0号舵机
//            config(0, (int) HEAD_LEFT, PERIOD_NS);
//        }
        // 调整0号舵机
        config(0, (int) HEAD_LEFT, PERIOD_NS);
    }

    /**
     * 头部转向右侧
     */
    public void head2Right() {
//        // 判断头部是否在右侧
//        if (SPUtils.getInstance().getInt(ROBOT_HEAD_POSITION, POSITION_MIDDLE) != POSITION_RIGHT) {
//            // 修改状态
//            SPUtils.getInstance().put(ROBOT_HEAD_POSITION, POSITION_RIGHT);
//            // 调整0号舵机
//            config(0, (int) HEAD_RIGHT, PERIOD_NS);
//        }
        // 调整0号舵机
        config(0, (int) HEAD_RIGHT, PERIOD_NS);
    }

    /**
     * 头部转向中间
     */
    public void head2Middle() {
//        int dutyNs = 0;
//        // 判断头部当前位置
//        switch (SPUtils.getInstance().getInt(ROBOT_HEAD_POSITION, POSITION_MIDDLE)) {
//            // 中间
//            case POSITION_MIDDLE:
//                break;
//            // 左侧
//            case POSITION_LEFT:
//                dutyNs = (int) HEAD_LEFT_2_MIDDLE;
//                break;
//            // 右侧
//            case POSITION_RIGHT:
//                dutyNs = (int) HEAD_RIGHT_2_MIDDLE;
//                break;
//            default:
//                break;
//        }
//        if (dutyNs != 0) {
//            // 标记舵机位置为头部
//            SPUtils.getInstance().put(ROBOT_HEAD_POSITION, POSITION_MIDDLE);
//            // 调整0号舵机
//            config(0, dutyNs, PERIOD_NS);
//        }
        // 调整0号舵机
        config(0, (int) HEAD_LEFT_2_MIDDLE, PERIOD_NS);
    }


    // 加载本地方法
    static {
        System.loadLibrary("Pwm_Motor");
    }

    /**
     * 功能：	 打开对应的Pwm Motor设备
     * 参数：
     * dev：	设备文件名（linux系统中为设备文件描述符）
     * 返回：	true：打开成功； false：打开失败
     */
    private native boolean open();//打开


    /**
     * 功能：	 关闭对应的Pwm Motor设备
     * 参数：	无
     * 返回：	无
     */
    private native void close();

    /**
     * 功能：	配置Pwm Motor相应的属性
     */
    private native boolean config(int pwm_id, int pwm_duty_ns, int pwm_period_ns);


    /**
     * 功能：	使能Pwm Motor
     */
    private native int enable(int pwm_id);

    /**
     * 功能：	失能Pwm Motor
     */
    private native int disable(int pwm_id);
}
