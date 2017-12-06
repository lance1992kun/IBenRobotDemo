package com.samton.ibenrobotdemo.net;

import android.support.annotation.NonNull;


import com.samton.IBenRobotSDK.utils.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/11/07
 *     desc   : 网络响应自定义拦截器
 *     version: 1.0
 * </pre>
 */

final class LoggingInterceptor implements Interceptor {
    @SuppressWarnings("ConstantConditions")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // 获得请求信息
        Request request = chain.request();
        // 打印请求消息体
        LogUtils.e(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        // 记录请求耗时
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        // 记录请求耗时
        long t2 = System.nanoTime();
        // 打印接受消息体
        LogUtils.e(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        // 消息类型和消息返回体
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        // 打印JSON消息
        LogUtils.json(LogUtils.E, content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
