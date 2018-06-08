package com.jyy.download.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wanxiang on 2018/5/26.
 */
@Entity
public class FileRange  {
    @Id(autoincrement = true)
    private  Long _id;
    private  long start;
    private  String fileName;
    private  long time;
    private  boolean isFinish;
    private  long length;

    @Generated(hash = 1804804804)
    public FileRange(Long _id, long start, String fileName, long time,
            boolean isFinish, long length) {
        this._id = _id;
        this.start = start;
        this.fileName = fileName;
        this.time = time;
        this.isFinish = isFinish;
        this.length = length;
    }

    @Generated(hash = 1807480922)
    public FileRange() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
}



