package com.jyy.download.util;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by jiaoyanan on 2018/5/28.
 */

public class FileUtil {
    /**
     * 检查文件(文件夹)是否存在,返回路径
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }
    /**
     * 检查文件(文件夹)是否存在,返回结果
     */
    public static File checkDirFile(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return null;
        }
        File dir = new File(dirPath);
        if (dir.exists()) {
            return dir;
        }
        return null;
    }

}
