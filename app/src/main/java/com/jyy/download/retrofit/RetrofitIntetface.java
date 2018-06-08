package com.jyy.download.retrofit;


import com.jyy.download.util.AppConstant;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by jiaoyanan on 2018/3/12.
 */

public interface RetrofitIntetface {


    /**
     *  断点下载
     * @param start 起始位
     * @param url 链接
     * @return
     */

    //多个baseurl使用，单个baseUrl无需配置在此配置
    @Headers({"baseUrl:"+ AppConstant.BASE_HTTP})
    //特别注意，防止加载到内存中
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);



}
