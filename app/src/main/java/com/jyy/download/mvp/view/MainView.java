package com.jyy.download.mvp.view;

/**
 * Created by wanxiang on 2018/5/25.
 */

public interface MainView {

    void onProgress(String url,int progress);

    void onPermission(boolean result);

}
