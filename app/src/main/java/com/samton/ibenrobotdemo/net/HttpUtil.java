package com.samton.ibenrobotdemo.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.samton.ibenrobotdemo.net.HttpUrl.BASE_URL;


/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/04/14
 *     desc   : 联网工具类
 *     version: 1.0
 * </pre>
 */

public class HttpUtil {
    /**
     * 联网帮助类单例
     */
    private static final HttpUtil instance = new HttpUtil();
    /**
     * 联网请求
     */
    private Retrofit mRetrofit;

    /**
     * 获取单例
     *
     * @return 联网单例
     */
    public static HttpUtil getInstance() {
        return instance;
    }

    /**
     * 私有构造
     */
    private HttpUtil() {
        mRetrofit = createRetrofit();
    }

    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 创建网络管理对象
     *
     * @return 网络管理对象
     */
    private Retrofit createRetrofit() {
        // 自定义OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 网络请求拦截器
                .addInterceptor(new LoggingInterceptor())
                // 访问HTTPS
                // .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                // 连接超时>>>10秒
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                // 读取超时>>>10秒
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        // 返回 Retrofit 对象
        return new Retrofit.Builder()
                // 联网地址
                .baseUrl(BASE_URL)
                // 自定义OkHttpClient
                .client(okHttpClient)
                // 添加Gson转换工厂
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava2调用适配工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
}
