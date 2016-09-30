package com.zcy.ghost.vivideo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.zcy.ghost.vivideo.BuildConfig;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Locale;

/**
 * Description: LogUtils
 * Creator: yxc
 * date: 2016/9/7 10:15
 */
public class LogUtils {

    private static boolean REDIRECTTO_FILE = false;

    public static String logFilePrefix = "LogInfo";

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            try {
                Log.v(tag, msg);
                logMsg(tag, msg);
            } catch (Exception e) {
            }
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            try {
                Log.i(tag, msg);
                logMsg(tag, msg);
            } catch (Exception e) {
            }
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            try {
                Log.i(tag, msg, tr);
                logException(tag, msg, tr);
            } catch (Exception e) {
            }
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(tag, msg);
                logMsg(tag, msg);
            } catch (Exception e) {
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(tag, msg, tr);
                logException(tag, msg, tr);
            } catch (Exception e) {
            }
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            try {

                Log.w(tag, msg);
                logMsg(tag, msg);
            } catch (Exception e) {
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            try {
                Log.w(tag, msg, tr);
                logException(tag, msg, tr);
            } catch (Exception e) {
            }
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            try {
                Log.e(tag, msg);
                logMsg(tag, msg);
            } catch (Exception e) {
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            try {
                Log.e(tag, msg, tr);
                logException(tag, msg, tr);
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint("WorldWriteableFiles")
    @SuppressWarnings("deprecation")
    public static void initLog(Activity activity) {
        if (REDIRECTTO_FILE && BuildConfig.DEBUG) {
            PrintStream out = null;
            try {
                out = new PrintStream(activity.openFileOutput(logFilePrefix + System.currentTimeMillis() + ".log", Context.MODE_WORLD_WRITEABLE),
                        true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.setOut(out);
            System.setErr(out);
        }
    }

    private static void logMsg(String tag, String msg) {
        if (REDIRECTTO_FILE) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.CHINA);
            String date = sdf.format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(date);
            sb.append("|");
            sb.append("Thread|");
            sb.append(Thread.currentThread().getId());
            sb.append(":");
            sb.append(tag);
            sb.append("=>");
            sb.append(msg);
            System.out.println(sb.toString());
        }
    }

    private static void logException(String tag, String msg, Throwable tr) {
        if (REDIRECTTO_FILE) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.CHINA);
            String date = sdf.format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(date);
            sb.append("|");
            sb.append("Thread|");
            sb.append(Thread.currentThread().getId());
            sb.append(":");
            sb.append(tag);
            sb.append("=>");
            sb.append(msg);
            sb.append("=>Exception:");
            sb.append(tr);
            System.out.println(sb.toString());
        }
    }

    @SuppressWarnings("rawtypes")
    public static void dumpMap(String tag, AbstractMap m) {
        d(tag, "dump map information");
        for (Object obj : m.keySet())
            d(tag, "key:" + obj + ";\tvalue:" + m.get(obj));
    }

}
