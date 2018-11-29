package com.jyy.download.retrofit;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.jyy.download.bean.DownResult;
import com.jyy.download.greendao.DaoManagement;
import com.jyy.download.greendao.FileRange;
import com.jyy.download.mvp.base.BaseObserver;
import com.jyy.download.util.FileUtil;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by wanxiang on 2018/5/25.
 */

public class RetrofitDownLoadManager {

    //文件总长度
    private long mFileLength;
    //待下载文件
    private File mFile;
    //上下文
    private Context mcontext;
    private BaseObserver<ResponseBody> mObserver;
    private String mFileName;
    private DaoManagement mGreenDao;
    private FileRange mFileRange;
    private boolean flag;

    public RetrofitDownLoadManager(Context mcontext) {
        this.mcontext = mcontext;

    }

    /**
     * @param mFileName
     * @return
     */
    public long getFileStart(DaoManagement mGreenDao, String mFileName) {


        try {
            this.mGreenDao = mGreenDao;
            this.mFileName = mFileName;
            mFileRange = mGreenDao.query(mFileName);
            //检查待下载文件是否存在
            String basePath = FileUtil.checkDirPath(Environment.getExternalStorageDirectory() + File.separator + mcontext.getPackageName());
            mFile = new File(basePath + File.separator + mFileName);

            if (!mFile.exists()) {
                //文件不存在，从0开始下载
                mFile.createNewFile();
                if (mFileRange != null) {
                    mGreenDao.delete(mFileRange);
                }
                return 0;
            } else {
                if (mFileRange != null) {
                    //文件存在，已经下载完成
                    if (mFileRange.getIsFinish()) {
                        //完整性校验
                        //取代事务车结果回调，完成解耦
                        mObserver.onResultCallBack(new DownResult(true, mFileName));
                        return -1;
                    } else {
                        //文件下载未完成，从断点处下载
                        mFileLength = mFileRange.getLength();
                        return mFileRange.getStart();
                    }

                } else {
                    mFile.delete();
                    mFile.createNewFile();
                    if (mFileRange != null) {
                        mGreenDao.delete(mFileRange);
                    }
                    return 0;
                }

            }
        } catch (IOException e) {
            return -2;
        }
    }

    public boolean writeStream(long mStart, ResponseBody mBody, BaseObserver<ResponseBody> mObserver) {
        this.mObserver = mObserver;

        if (mFile == null) {
            return false;
        }

        if (mStart > 0 && mFileLength > 0 && mStart == mFileLength) {
            return true;
        }
        //从0开始下载，数据库插入数据
        if (mStart == 0) {
            mFileRange = new FileRange();
            mFileRange.setFileName(mFileName);
            mFileRange.setIsFinish(false);
            mFileRange.setLength(mBody.contentLength());
            mFileRange.setStart(mStart);
            mFileRange.setTime(System.currentTimeMillis());
            mGreenDao.update(mFileRange);
            mFileLength = mBody.contentLength();
        }

        InputStream mInputStream = null;
        OutputStream mOutputStream = null;
        try {
            byte[] mReader = new byte[1024 * 1024];
            //边读边写
            mInputStream = mBody.byteStream();
            mOutputStream = new FileOutputStream(mFile, true);
            while (true) {
                int read = mInputStream.read(mReader);
                if (read == -1) {
                    break;
                }
                mOutputStream.write(mReader, 0, read);
                mStart += read;
                //进度回调
                int num = (int) (mStart * 100 / mFileLength);
                mObserver.onSetProgress(mFileName, num);
                Log.i("TAG", "进度回调" + ":" + mStart + "  " + num);
            }
            mOutputStream.flush();
            flag = true;
            //更新数据库数据
            mFileRange.setFileName(mFileName);
            mFileRange.setIsFinish(true);
            mFileRange.setStart(mStart);
            mFileRange.setTime(System.currentTimeMillis());
            mGreenDao.update(mFileRange);
            //此处可以进行文件完整性校验(尤其针对APK文件,否则有可能无法安装)
            Log.i("TAG", mFileName + ": 下载完成……");
            //取代事务车结果回调，完成解耦
            mObserver.onResultCallBack(new DownResult(true, mFileName));
            return true;
        } catch (IOException e) {
            Log.i("TAG", mFileName + ": 下载失败……");
            return false;
        } finally {
            //保存进度到本地数据库
            if (!flag) {
                mFileRange.setFileName(mFileName);
                mFileRange.setStart(mStart);
                mFileRange.setTime(System.currentTimeMillis());
                mGreenDao.update(mFileRange);
            }

            try {
                if (mInputStream != null) {
                    mInputStream.close();

                }
                if (mOutputStream != null) {
                    mOutputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
