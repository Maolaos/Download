package com.jyy.download.bean;

/**
 * Created by wanxiang on 2018/5/26.
 */

public class DownResult {
    private boolean result ;
    private String fileName;

    public DownResult( boolean result,String fileName){
        this.result=result;
        this.fileName=fileName;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
