package com.jyy.download.mvp.presenter;

import android.Manifest;
import android.content.Context;

import com.jyy.download.greendao.FileRange;
import com.jyy.download.mvp.base.BaseObserver;
import com.jyy.download.mvp.base.BasePresenter;
import com.jyy.download.mvp.view.MainView;
import com.jyy.download.retrofit.RetrofitDownLoadManager;
import com.tbruyelle.rxpermissions2.Permission;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by jiaoyanan on 2018/5/25.
 */
public class MainPresenter extends BasePresenter<MainView>  {
    private Context mContext;
    private ConcurrentHashMap<String,Disposable> mMap;

    public MainPresenter(Context mContext) {
        super(mContext);
        this.mContext=mContext;
        this.mMap=new ConcurrentHashMap<>();


    }


    public void onDownloadStart(final  String url,final  String fileName){

        boolean flag=mPermissions.isGranted(Manifest.permission_group.STORAGE);
        if (!flag) {
            mPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        // 用户已经同意该权限
                       download(url,fileName);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』
                        mView.onPermission(false);

                    } else {
                        mView.onPermission(false);
                    }
                }
            });
        }else{
            download(url,fileName);
        }


    }


    private void download(String url,final  String fileName){
        final  RetrofitDownLoadManager manager=new RetrofitDownLoadManager(mContext);
        final  long start=manager.getFileStart(mGreenDao,fileName);

        //下载完成
        if (start==-1)

            return;
        //异常
        if (start==-2)
            return;

        mRetrofit.onDownload("bytes=" + start + "-", url, new BaseObserver<ResponseBody>(mContext) {
            @Override
            protected void onError(String error) {

            }

            @Override
            protected void onSuccess(final ResponseBody result) {

                if (manager.writeStream(start,result,this)){
                    onDownloadStop(fileName);
                }

            }

            @Override
            protected void onAddSubscribe(Disposable d) {
                mMap.put(fileName,d);
            }

            @Override
            protected void onProgress(String fileName, int progress) {

                mView.onProgress(fileName,progress);
            }




        });
    }
   public void onDownloadStop(String flieName){
      Disposable disposable=mMap.get(flieName);
       //取消订阅，是文件停止下载
       if (disposable!=null&&!disposable.isDisposed()){
           disposable.dispose();
       }

   }
    @Override
    public void clearRequest() {
        for (Disposable disposable: mMap.values()) {
            if (!disposable.isDisposed()){
                disposable.dispose();
            }

        }
    }

}
