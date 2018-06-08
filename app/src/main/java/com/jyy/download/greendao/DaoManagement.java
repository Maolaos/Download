package com.jyy.download.greendao;

import android.content.Context;

import com.jyy.download.util.AppConstant;

/**
 * Created by jiaoyanan on 2018/5/27.
 */

public class DaoManagement {
    private static DaoManagement instance=null;
    private FileRangeDao frd;
    public static DaoManagement getInstance() {
        if (instance == null) {
            synchronized (DaoManagement.class) {
                if (instance == null) {
                    instance = new DaoManagement();
                }
            }
        }
        return instance;
    }


    public void init (Context mContext){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, AppConstant.DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        frd=daoSession.getFileRangeDao();

    }


    public void update(FileRange fr){
        if (query(fr.getFileName())!=null){
            frd.update(fr);
        }else {
            frd.insert(fr);
        }

    }

    public void delete(FileRange fr){
        if (query(fr.getFileName())!=null){
            frd.delete(fr);
        }
    }

    public FileRange query(String fileName){

        FileRange fr= frd.queryBuilder()
                .where(FileRangeDao.Properties.FileName.eq(fileName))
                .unique();

        return fr;
    }





}
