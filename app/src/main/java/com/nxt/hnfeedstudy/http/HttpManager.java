package com.nxt.hnfeedstudy.http;


import com.nxt.hnfeedstudy.MyConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {

    private static final int DEFAULT_TIMEOUT = 10;
    private static HttpService singleton;


    public static HttpService getRetrofit() {
        if (singleton == null) {
            synchronized (HttpManager.class) {
                singleton = createRetrofit().create(HttpService.class);
            }
        }
        return singleton;
    }

    private static Retrofit createRetrofit() {
//        File cacheFile = new File(DemoApplication.getContext().getExternalCacheDir(), "WuXiaolongCache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
//                        .newBuilder()
//                        .header("Charset","UTF-8")
//                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .build()
                        ;
//                if (!AppUtils.networkIsAvailable(DemoApplication.getContext())) {
//                    request = request.newBuilder()
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
                Response response = chain.proceed(request);
//                if (AppUtils.networkIsAvailable(DemoApplication.getContext())) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
//                             .header("Charset", "UTF-8")
//                        .header("Content-Type", "application/json")
                            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                            .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                        .header("Cache-Control","no-cache")
                        .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("nyn")
//                            .build();
//                }
                return response;
            }
        };
//        builder.cache(cache).addInterceptor(cacheInterceptor);//缓存

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(120, TimeUnit.SECONDS);
//        builder.addInterceptor(cacheInterceptor);//去除缓存
//        builder.addInterceptor(logging);//打印日志


        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MyConstant.IP)
                .build();
        return retrofit;
    }


}
