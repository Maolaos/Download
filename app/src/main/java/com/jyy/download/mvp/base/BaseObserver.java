package com.jyy.download.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.jyy.download.bean.DownResult;
import com.jyy.download.util.CommonUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiaoyanan on 2018/5/25.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private Context mContext;

    public BaseObserver(Context mContext) {
        super();
        this.mContext = mContext;

    }


    @Override
    public void onSubscribe(Disposable d) {
        if (!CommonUtil.isNetWorkConnected(mContext)) {
            d.dispose();
            onError("网络异常，请检查网络……");
            onComplete();
            return;

        }
        onAddSubscribe(d);

    }

    @Override
    public void onError(Throwable e) {
        onError("请求异常");

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {


        onSuccess((T) t);
    }

    protected abstract void onError(String error);

    protected abstract void onSuccess(T result);

    protected abstract void onAddSubscribe(Disposable d);

    protected abstract void onProgress(String fileName,int progress);

    protected abstract void onResult(DownResult result);


    // 监听进度
    public void onSetProgress(String fileName,int progress) {
        onProgress(fileName,progress);

    }
    //下载结果回调
    public void onResultCallBack(final DownResult result){
        Activity activity=(Activity) mContext;
        //特别注意，文件读写是在Io线程中，结果回调要在主线程中完成
     activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onResult(result);
            }
        });

    }




}
