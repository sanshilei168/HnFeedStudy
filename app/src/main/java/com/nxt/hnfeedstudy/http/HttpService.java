package com.nxt.hnfeedstudy.http;


import com.nxt.hnfeedstudy.MyConstant;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * service统一接口数据
 * Created by WZG on 2016/7/16.
 */
public interface HttpService {


    /**
     * 版本更新
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(MyConstant.UPDATE_URL)
    Call<ResponseBody> HttpVesionUpdate(@FieldMap(encoded = true) Map<String, String> params);

    /**
     * 版本更新 下载apk
     * @return
     */
    @GET
    Call<ResponseBody> HttpUpdateApk(@Url String mUrl);


}
