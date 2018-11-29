package com.jyy.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.jyy.download.bean.DownResult;
import com.jyy.download.mvp.base.BaseActivity;
import com.jyy.download.mvp.presenter.MainPresenter;
import com.jyy.download.mvp.view.MainView;
import com.jyy.download.util.HttpConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.progressbar.BGAProgressBar;

public class MainActivity extends BaseActivity<MainView,MainPresenter> implements MainView{
    @BindView(R.id.btn_main_load_one)
    Button btn_main_load_one;
    @BindView(R.id.btn_main_load_two)
    Button btn_main_load_two;
    @BindView(R.id.btn_main_load_three)
    Button btn_main_load_three;
    @BindView(R.id.pg_main_progress_one)
    BGAProgressBar  pg_main_progress_one;
    @BindView(R.id.pg_main_progress_two)
    BGAProgressBar  pg_main_progress_two;
    @BindView(R.id.pg_main_progress_three)
    BGAProgressBar  pg_main_progress_three;

    private static  final  String FILE_1="test_1.apk";
    private static  final  String FILE_2="test_2.apk";
    private static  final  String FILE_3="test_3.apk";

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        unbinder= ButterKnife.bind(this);




        btn_main_load_one.setOnClickListener(onClickListener);
        btn_main_load_two.setOnClickListener(onClickListener);
        btn_main_load_three.setOnClickListener(onClickListener);

    }

    @Override
    public void onProgress(String fileName,int progress) {
        if (fileName.equals(FILE_1)){
            pg_main_progress_one.setProgress(progress);
        }else if (fileName.equals(FILE_2)){
            pg_main_progress_two.setProgress(progress);
        }else if (fileName.equals(FILE_3)){
            pg_main_progress_three.setProgress(progress);
        }
    }

    @Override
    public void onPermission(boolean result) {

        if (!result){
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivity(intent);

        }

    }
    //下载结果回调
    @Override
    public void onDownloadResult(DownResult result) {
        if (result.isResult()){
            if (result.getFileName().equals(FILE_1)){
                btn_main_load_one.setText("下载完成");
                btn_main_load_one.setEnabled(false);
            }else if (result.getFileName().equals(FILE_2)){
                btn_main_load_two.setText("下载完成");
                btn_main_load_two.setEnabled(false);
            }else if (result.getFileName().equals(FILE_3)){
                btn_main_load_three.setText("下载完成");
                btn_main_load_three.setEnabled(false);
            }


        }
    }



    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           switch (v.getId()){
               case R.id.btn_main_load_one:
               if (btn_main_load_one.getText().toString().equals("下载")){
                   btn_main_load_one.setText("暂停");
                   mPresenter.onDownloadStart(HttpConstant.APK_URL_ONE,FILE_1);
               }else{
                   btn_main_load_one.setText("下载");
                   mPresenter.onDownloadStop(FILE_1);
               }
                   break;
               case R.id.btn_main_load_two:
               if (btn_main_load_two.getText().toString().equals("下载")){
                   btn_main_load_two.setText("暂停");
                   mPresenter.onDownloadStart(HttpConstant.APK_URL_TWO,FILE_2);
               }else{
                   btn_main_load_two.setText("下载");
                   mPresenter.onDownloadStop(FILE_2);
               }
                   break;
               case R.id.btn_main_load_three:
               if (btn_main_load_three.getText().toString().equals("下载")){
                   btn_main_load_three.setText("暂停");
                   mPresenter.onDownloadStart(HttpConstant.APK_URL_THREE,FILE_3);
               }else{
                   btn_main_load_three.setText("下载");
                   mPresenter.onDownloadStop(FILE_3);
               }
                   break;
           }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
