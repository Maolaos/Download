package com.jyy.download.mvp.base;

import android.app.Activity;
import android.content.Context;

import com.jyy.download.greendao.DaoManagement;
import com.jyy.download.retrofit.RetrofitManagement;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by jiaoyanan on 2018/5/25.
 */

public abstract class BasePresenter<T> {
    protected T mView;
    protected RetrofitManagement mRetrofit;
    protected DaoManagement mGreenDao;
    protected RxPermissions mPermissions;


    public BasePresenter(Context mContext) {
        mGreenDao=DaoManagement.getInstance();
        mGreenDao.init(mContext);
        mPermissions=new RxPermissions((Activity) mContext);

    }
    /**
     * 绑定view
     *
     * @param view
     */
    public void attachView(T view){
        mRetrofit=RetrofitManagement.getInstance();
        this.mView = view;
    }

    /**
     * 解除和view的绑定
     */
    public void detachView() {
        if (mView != null) {
            mView = null;
        }

    }

    /**
     * 获取绑定的view
     *
     * @return
     */
    public T getView() {
        return mView;
    }

    /**
     * 清空请求
     *
     */
    public abstract void clearRequest();



}

