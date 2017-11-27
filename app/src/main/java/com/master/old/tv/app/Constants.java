package com.master.old.tv.app;

import android.os.Environment;

import java.io.File;

/**
 * Description: Constants
 * Creator: yxc
 * date: 2016/9/21 10:05
 */
public class Constants {
    //================= PATH ====================
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";

    public static final String PRIMARYCOLOR = "PRIMARYCOLOR";
    public static final String TITLECOLOR = "TITLECOLOR";
    public static final String DISCOVERlASTpAGE = "DISCOVERlASTpAGE";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "codeest" + File.separator + "GeekNews";
}
