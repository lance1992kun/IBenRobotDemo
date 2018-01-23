package com.samton.ibenrobotdemo.data;

/**
 * <pre>
 *   author  : syk
 *   e-mail  : shenyukun1024@gmail.com
 *   time    : 2018/01/23 16:11
 *   desc    : 串口发送数据帮助类
 *   version : 1.0
 * </pre>
 */

public class SerialMsgHelper {
    /**
     * 获取头部串口发送数据
     *
     * @param action 动作
     * @return 发送数据
     */
    public static String getHeadMsg(Action action) {
        String result = "";
        switch (action) {
            case HEAD_LEFT:
                result = "{SH020300}";
                break;
            case HEAD_MIDDLE:
                result = "{SH090300}";
                break;
            case HEAD_RIGHT:
                result = "{SH160300}";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取手臂串口发送数据
     *
     * @param action 具体的动作
     * @return 发送数据
     */
    public static String getArmMsg(Action action) {
        String result = "";
        switch (action) {
            case LEFT_ARM_UP:
                result = "{SL180300}";
                break;
            case LEFT_ARM_DOWN:
                result = "{SL090300}";
                break;
            case RIGHT_ARM_UP:
                result = "{SR180300}";
                break;
            case RIGHT_ARM_DOWN:
                result = "{SR090300}";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取胳膊串口发送数据(手臂30-255度,头部20-160度)
     *
     * @param action 动作
     * @param angle  角度
     * @return 发送数据
     */
    public static String getAngelMsg(Action action, int angle) {
        String result = "";
        switch (action) {
            case HEAD:
                result = "{SH" + angle + "300}";
                break;
            case LEFT_ARM:
                result = "{SL" + angle + "300}";
                break;
            case RIGHT_ARM:
                result = "{SR" + angle + "300}";
                break;
            default:
                break;
        }
        return result;
    }

    public enum Action {
        HEAD_LEFT(0), HEAD_MIDDLE(1), HEAD_RIGHT(2),
        LEFT_ARM_UP(3), LEFT_ARM_DOWN(4), RIGHT_ARM_UP(5), RIGHT_ARM_DOWN(6),
        HEAD(7), LEFT_ARM(8), RIGHT_ARM(9);
        private int direction;

        Action(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }
    }
}
