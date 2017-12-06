package com.samton.ibenrobotdemo.net;

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
    static final String BASE_URL = "http://114.55.111.3/";
    /**
     * 初始化机器人信息接口
     * 正式地址 robotInfo/robotInit
     * 测试地址 XiaoBenManager/robotInfo/robotInit
     */
    final static String INIT_ROBOT_INFO = "XiaoBenManager/robotInfo/robotInit";
    /**
     * 新建地图接口
     * 正式地址 scene/addScene
     * 测试地址 XiaoBenManager/scene/addScene
     */
    final static String ADD_SCENE = "scene/addScene";
    /**
     * 修改地图接口
     * 正式地址 scene/updateScene
     * 测试地址 XiaoBenManager/scene/updateScene
     */
    final static String UPDATE_SCENE = "XiaoBenManager/scene/updateScene";
}
