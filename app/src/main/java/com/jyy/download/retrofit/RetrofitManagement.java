package com.jyy.download.retrofit;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jyy.download.mvp.base.BaseObserver;
import com.jyy.download.util.HttpConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by jiaoyanan on 2018/3/9.
 */

public class RetrofitManagement {

    private RetrofitIntetface intetface;

    public static RetrofitManagement instance = null;

    public static RetrofitManagement getInstance() {
        if (instance == null) {
            synchronized (RetrofitManagement.class) {
                if (instance == null) {
                    instance = new RetrofitManagement();
                }
            }
        }
        return instance;
    }

    private RetrofitManagement() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(new MoreBaseUrlInterceptor());
        OkHttpClient client = builder.build();
        Retrofit.Builder rBuilder = new Retrofit.Builder();
        rBuilder.client(client);
        rBuilder.baseUrl(HttpConstant.BASEHTTP);
        rBuilder.addConverterFactory(ScalarsConverterFactory.create());
        rBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = rBuilder.build();
        intetface = retrofit.create(RetrofitIntetface.class);
    }



    public void onDownload(String start,String url,BaseObserver<ResponseBody> observer){
        intetface.download(start,url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }

}
