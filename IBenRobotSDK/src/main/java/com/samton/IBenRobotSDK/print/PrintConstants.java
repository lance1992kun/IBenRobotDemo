package com.samton.IBenRobotSDK.print;

/**
 * Created by lhg on 2017/7/15.
 * 打印机参数
 */
public class PrintConstants {
    /**
     * 打印字体大小  具体值代表的意思 n - 范围0-255；n的0到3位设定字符高度，4-7位用来设定字符宽度
     * 如 二级制 1100110  十进制 102   代表 高6 宽 6
     */
    public static int FONT_SIZEE_0 = 0;
    public static int FONT_SIZEE_1 = 17;
    public static int FONT_SIZEE_2 = 34;
    public static int FONT_SIZEE_3 = 51;
    public static int FONT_SIZEE_4 = 68;
    public static int FONT_SIZEE_5 = 85;
    public static int FONT_SIZEE_6 = 102;
    /**
     * 打印位置
     */
    public static int PRINT_LEFT = 0;
    public static int PRINT_CENTER = 1;
    public static int PRINT_RIGHT = 2;

    public static final String PRINT_ADDRESS = "IBEN_PRINT_ADDRESS";
}
