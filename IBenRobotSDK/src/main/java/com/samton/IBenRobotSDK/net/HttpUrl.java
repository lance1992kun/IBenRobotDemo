package com.samton.IBenRobotSDK.net;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/07
 *     desc   : 联网地址常量
 *     version: 1.0
 * </pre>
 */

public final class HttpUrl {
    /**
     * 服务器地址
     * 正式地址 http://114.55.111.3/
     * 测试地址 http://121.41.40.145:7080/
     */
    static final String BASE_URL = "http://121.41.40.145:7080/";
    /**
     * 与小笨聊天的地址接口
     * 正式地址 iben_qa/RobotQADispatcher
     * 测试地址 iben_qa/RobotQADispatcher
     */
    final static String CHAT = "iben_qa/RobotQADispatcher";
    /**
     * 激活机器人信息接口
     * 正式地址 robotInfo/activeRobot
     * 测试地址 XiaoBenManager/robotInfo/activeRobot
     */
    final static String ADD_ROBOT_INFO = "XiaoBenManager/robotInfo/activeRobot";
    /**
     * 初始化机器人信息接口
     * 正式地址 robotInfo/robotInitNew
     * 测试地址 XiaoBenManager/robotInfo/robotInitNew
     */
    final static String INIT_ROBOT_INFO = "XiaoBenManager/robotInfo/robotInitNew";
    /**
     * 获取开关状态接口
     * 正式地址 robotInfo/getRobotChatFlag
     * 测试地址 XiaoBenManager/robotInfo/getRobotChatFlag
     */
    final static String GET_ROBOT_CHAT_FLAG = "XiaoBenManager/robotInfo/getRobotChatFlag";
    /**
     * 富文本连接头
     * 正式地址 resources/views/show.html?content=
     * 测试地址 XiaoBenManager/resources/views/show.html?content=
     */
    public final static String RICH_HEADER = "resources/views/show.html?content=";
}
