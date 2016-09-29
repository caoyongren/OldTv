package com.zcy.ghost.vivideo.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.zcy.ghost.vivideo.BuildConfig;

import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;


/**
 * Log日志输出， 子类是 KL
 * <p/>
 * <p/>
 * 1.多彩格式的日志，提供关键信息；
 * <p/>
 * 2.和slf4j  相同格式，统一管理；
 * <p/>
 * 3.抽离简化日志功能。
 * <p/>
 * Created by Caopeng on
 */
public class L {

    private static final String TAG = "TAG";

    private static WeakHashMap<String, org.slf4j.Logger> loggerHashMap;

    /**
     * 获取 创建Logger 的时候的实例，提供利用率
     *
     * @param className logger的名称
     * @return
     */
    private static org.slf4j.Logger getLoggerInstance(String className) {
        if (loggerHashMap == null)
            loggerHashMap = new WeakHashMap<String, org.slf4j.Logger>();

        if (loggerHashMap.containsKey(className)) {
            return loggerHashMap.get(className);
        } else {
            org.slf4j.Logger logger = LoggerFactory.getLogger(className);
            loggerHashMap.put(className, logger);
            return logger;
        }
    }

    /**
     * @param classObj
     * @param messageForI 统一使用  {} 占位符
     * @param args
     */
    public static void i(Class classObj, String messageForI, Object... args) {
        String className = verifyClass(classObj);
        i(className, messageForI, args);
    }

    private static void i(String className, String messageForI, Object... args) {
        if (!BuildConfig.DEBUG) {
            org.slf4j.Logger logger = getLoggerInstance(className);
            if (logger == null) {
                return;
            }
            logger.info(messageForI, args);
        } else {
            Logger.i(handleDebugLogFormat(messageForI), args);
        }
    }

    /**
     * @param classObj
     * @param messageForD 统一使用  {} 占位符
     * @param args
     */
    public static void d(Class classObj, String messageForD, Object... args) {
        String className = verifyClass(classObj);
        d(className, messageForD, args);
    }

    private static void d(String className, String messageForD, Object... args) {
        if (!BuildConfig.DEBUG) {
            org.slf4j.Logger logger = getLoggerInstance(className);
            if (logger == null) {
                return;
            }
            logger.debug(messageForD, args);
        } else {
            Logger.d(handleDebugLogFormat(messageForD), args);
        }
    }

    /**
     * @param classObj
     * @param messageForW 统一使用  {} 占位符
     * @param args
     */
    public static void w(Class classObj, String messageForW, Object... args) {
        String className = verifyClass(classObj);
        w(className, messageForW, args);
    }

    private static void w(String className, String messageForW, Object... args) {
        if (!BuildConfig.DEBUG) {
            org.slf4j.Logger logger = getLoggerInstance(className);
            if (logger == null) {
                return;
            }
            logger.warn(messageForW, args);
        } else {
            Logger.w(handleDebugLogFormat(messageForW), args);
        }
    }


    /**
     * @param classObj
     * @param messageForE 统一使用  {} 占位符
     * @param args
     */
    public static void e(Class classObj, String messageForE, Object... args) {
        String className = verifyClass(classObj);
        e(className, messageForE, args);
    }

    private static void e(String className, String messageForE, Object... args) {
        if (!BuildConfig.DEBUG) {
            org.slf4j.Logger logger = getLoggerInstance(className);
            if (logger == null) {
                return;
            }
            logger.error(messageForE, args);
        } else {
            Logger.e(handleDebugLogFormat(messageForE), args);
        }
    }

    @NonNull
    private static String verifyClass(Class classObj) {
        String className = TAG;

        if (classObj != null) {
            className = classObj.getSimpleName();
        }
        return className;
    }

    /**
     * 处理Debug模式下的打印log格式
     *
     * @param message
     * @return
     */
    private static String handleDebugLogFormat(String message) {
        return message.replace("{}", "%s");
    }

    /**
     * debug 模式下，会打印json 数据
     *
     * @param messageForE
     */
    public static void json(String messageForE) {
        if (BuildConfig.DEBUG) {
            Logger.json(messageForE);
        }
    }


    /**
     * 不需要可以移除
     */
    public static void removeLogger(Class classObj) {
        removeLogger(classObj.getSimpleName());
    }

    /**
     * 删除所有Logger持有的对象
     */
    public static void removeAllLogger() {
        if (loggerHashMap != null) {
            loggerHashMap.clear();
        }
    }

    private static void removeLogger(String className) {
        if (loggerHashMap != null && !TextUtils.isEmpty(className)) {
            loggerHashMap.remove(className);
        }
    }
}
