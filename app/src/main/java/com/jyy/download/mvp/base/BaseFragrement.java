package com.jyy.download.mvp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by jiaoyanan on 2018/5/25.
 */

public abstract class  BaseFragrement<V, T extends BasePresenter<V>> extends Fragment {
    /**
     * 和数据交互的presenter
     */
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = creatPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = creatView(inflater,container);
        iniView(view,savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T creatPresenter();

    /**
     * 初始化界面的布局的元素
     *
     * @param view
     */
    protected abstract void iniView(View view,Bundle savedInstanceState);

    /**
     * 初始化必要的数据
     */
    protected abstract void initData();

    /**
     * 创建fragrement的跟view
     *
     * @param inflater
     * @return
     */
    protected abstract View creatView(LayoutInflater inflater, ViewGroup container);


}
