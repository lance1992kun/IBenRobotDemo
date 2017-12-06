package com.samton.IBenRobotSDK.net;

import com.samton.IBenRobotSDK.data.ActiveBean;
import com.samton.IBenRobotSDK.data.ChatFlagBean;
import com.samton.IBenRobotSDK.data.InitBean;
import com.samton.IBenRobotSDK.data.MessageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.samton.IBenRobotSDK.net.HttpUrl.ADD_ROBOT_INFO;
import static com.samton.IBenRobotSDK.net.HttpUrl.CHAT;
import static com.samton.IBenRobotSDK.net.HttpUrl.GET_ROBOT_CHAT_FLAG;
import static com.samton.IBenRobotSDK.net.HttpUrl.INIT_ROBOT_INFO;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 联网接口
 *     version: 1.0
 * </pre>
 */

public interface HttpRequest {
    /**
     * 查询机器人聊天状态
     *
     * @param robUuid 机器人ID
     * @return 机器人聊天状态观察者对象
     */
    @GET(GET_ROBOT_CHAT_FLAG)
    Observable<ChatFlagBean> getRobotChatFlag(@Query("robUuid") String robUuid);

    /**
     * 跟小笨聊天
     *
     * @param appKey  机器人ID
     * @param time    当前时间戳
     * @param message 聊天信息
     * @return 跟小笨聊天的观察者对象
     */
    @GET(CHAT)
    Observable<MessageBean> send2IBen(@Query("appKey") String appKey, @Query("time") String time,
                                      @Query("message") String message);

    /**
     * 激活机器人
     *
     * @param appKey 机器人ID
     * @return 激活机器人观察者对象
     */
    @GET(ADD_ROBOT_INFO)
    Observable<ActiveBean> activeRobot(@Query("robUuid") String appKey);

    /**
     * 初始化机器人
     *
     * @param appKey 机器人ID
     * @return 初始化机器人观察者对象
     */
    @GET(INIT_ROBOT_INFO)
    Observable<InitBean> initRobot(@Query("robotUUID") String appKey);
}
