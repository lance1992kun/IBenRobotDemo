package com.samton.ibenrobotdemo.net;


import com.samton.ibenrobotdemo.data.UploadMapBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static com.samton.ibenrobotdemo.net.HttpUrl.ADD_SCENE;
import static com.samton.ibenrobotdemo.net.HttpUrl.UPDATE_SCENE;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/09/07
 *     desc   : 联网接口
 *     version: 1.0
 * </pre>
 */

public interface APIService {


    /**
     * 上传地图文件(创建)
     *
     * @param appKey         机器人ID
     * @param sceneName      地图名字
     * @param positionPoints 地图中存在的点位
     * @param file           地图文件
     * @param imgFile        缩略图图片
     * @return 上传地图的观察者对象
     */
    @POST(ADD_SCENE)
    @Multipart
    Observable<UploadMapBean> addScene(@Part("robuuid") RequestBody appKey, @Part("sceneName") RequestBody sceneName,
                                       @Part("positionPoints") RequestBody positionPoints, @Part MultipartBody.Part file,
                                       @Part MultipartBody.Part imgFile);

    /**
     * 更新地图文件(创建过的)
     *
     * @param appKey         机器人ID
     * @param sceneName      地图名字
     * @param newSceneName   地图的新名字
     * @param positionPoints 地图中存在的点位
     * @param file           地图文件
     * @param imgFile        缩略图图片
     * @return 更新地图文件的观察者对象
     */
    @POST(UPDATE_SCENE)
    @Multipart
    Observable<UploadMapBean> updateScene(@Part("robuuid") RequestBody appKey, @Part("sceneName") RequestBody sceneName,
                                          @Part("newSceneName") RequestBody newSceneName, @Part("positionPoints") RequestBody positionPoints,
                                          @Part("file") MultipartBody.Part file, @Part MultipartBody.Part imgFile);
}
