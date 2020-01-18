package com.anysou.aslogger;

import android.util.Log;

/**
 * 作者：AnySou_HongDao
 * 时间：2020/1/9
 * 邮箱：anysou@126.com
 * 原创：WangJintao
 * 步骤：5
 * 日志显示与记录者
 *
 * import android.util.Log
 * 1.Log.v()：打印一些最为繁琐、意义不大的日志信息
 * 2.Log.d()：打印一些调试信息(logd+tab)
 * 3.Log.i()：打印一些比较重要的数据，可帮助你分析用户行为数据（logi+tab）
 * 4.Log.w()：打印一些警告信息，提示程序该处可能存在的风险(logw+tab)
 * 5.Log.e()：打印程序中的错误信息(loge+tab)
 */

public class ASLogger {

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String title, String msg) {
        String str = formatString("I",title, msg);
        if (ASLogIConfig.getInstance().getIsShowLog())
            Log.i(ASLogIConfig.getInstance().getTag(), str);
        if (ASLogIConfig.getInstance().getIsWriteLog())
            ASLogFileUtils.writeLog(str);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String title, String msg) {
        String str = formatString("W",title, msg);
        if (ASLogIConfig.getInstance().getIsShowLog())
            Log.w(ASLogIConfig.getInstance().getTag(), str);
        if (ASLogIConfig.getInstance().getIsWriteLog())
            ASLogFileUtils.writeLog(str);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String title, String msg) {
        String str = formatString("E",title, msg);
        if (ASLogIConfig.getInstance().getIsShowLog())
            Log.e(ASLogIConfig.getInstance().getTag(), str);
        if (ASLogIConfig.getInstance().getIsWriteLog())
            ASLogFileUtils.writeLog(str);
    }

    public static String formatString(String mode, String title, String msg) {
        if (title == null) {
            return msg == null ? "" : mode+" "+msg;
        }
        return String.format(mode+" "+"[%s]: %s", title, msg == null ? "" : msg);
    }
}
