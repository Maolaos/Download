package com.jyy.download.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by jiaoyanan on 2018/5/25.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected ImageView mBack;
    protected TextView mNote;
    protected TextView mConfirm;
    protected TextView mBar;
    protected T mPresenter;
    private LinearLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "OnCreate_base");
        setContentView(getContentViewResId());
        //初始化Presenter
        mPresenter = createPresenter();
        //绑定presenter
        mPresenter.attachView((V) this);
        //初始化视图
        initView();
        //初始化数据
        initData(savedInstanceState);


    }

    protected abstract int getContentViewResId();


    /**
     * 创建presenter ,和数据层通信
     *
     * @return
     */
    protected abstract T createPresenter();

    protected abstract void initView();

    /**
     * 初始化数据信息
     */
    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //解绑presenter
        mPresenter.detachView();
        mPresenter.clearRequest();

    }



}

