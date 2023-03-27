package com.jye.devkit.log;

import android.os.Process;

import com.jye.devkit.log.printer.LogPrinter;

import java.util.List;

/**
 * 日志操作api
 *
 * @author jye
 * @since 1.0
 */
public class DkLog {

    private static final String LOG_PACKAGE;

    //static{}(即static块)，会在类被加载的时候执行且仅会被执行一次，一般用来初始化静态变量和调用静态方法，
    static {
        String className = DkLog.class.getName();
        LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    private static LogConfig sConfig;

    public static void init(LogConfig config) {
        sConfig = config;
    }

    public static LogConfig getConfig() {
        return sConfig;
    }

    /**
     * 打印debug级别的日志，可以传入格式化参数。
     */
    public static void d(String format, Object... args) {
        log(LogType.DEBUG, String.format(format, args));
    }

    /**
     * 打印debug级别的日志，可以传入格式化参数。（含tag）
     */
    public static void d(String tag, String format, Object... args) {
        log(LogType.DEBUG, tag, String.format(format, args));
    }

    /**
     * 打印info级别的日志，可以传入格式化参数。
     */
    public static void i(String format, Object... args) {
        log(LogType.INFO, String.format(format, args));
    }

    /**
     * 打印info级别的日志，可以传入格式化参数。（含tag）
     */
    public static void i(String tag, String format, Object... args) {
        log(LogType.INFO, tag, String.format(format, args));
    }

    /**
     * 打印warn级别的日志，可以传入格式化参数。
     */
    public static void w(String format, Object... args) {
        log(LogType.WARN, String.format(format, args));
    }

    /**
     * 打印warn级别的日志，可以传入格式化参数。（含tag）
     */
    public static void w(String tag, String format, Object... args) {
        log(LogType.WARN, tag, String.format(format, args));
    }

    /**
     * 打印error级别的日志，可以传入格式化参数。
     */
    public static void e(String format, Object... args) {
        log(LogType.ERROR, String.format(format, args));
    }

    /**
     * 打印error级别的日志，可以传入格式化参数。（含tag）
     */
    public static void e(String tag, String format, Object... args) {
        log(LogType.ERROR, tag, String.format(format, args));
    }

    public static void log(LogType type, Object content) {
        log(type, getConfig().getPrefix(), content);
    }

    public static void log(LogType type, String tag, Object content) {
        log(getConfig(), type, tag, content);
    }

    public static void log(LogConfig config, LogType type, String tag, Object content) {
        if (!config.isEnable()) {
            return;
        }

        //打印log
        List<LogPrinter> printers = config.getPrinters();
        for (LogPrinter printer : printers) {
            int myPid = Process.myPid();
            int myTid = Process.myTid();
            Thread thread = Thread.currentThread();
            StackTraceElement[] stackTrace = getRealStackTrack(new Throwable().getStackTrace(), LOG_PACKAGE);
            String finalTag = !config.getPrefix().isEmpty() ? config.getPrefix() + "-" + tag : tag;
            printer.print(config, new LogItem(type, finalTag, content.toString(), myPid, myTid, thread, stackTrace));
        }
    }

    /**
     * 获取除忽略包名之外的堆栈信息
     * Get the real stack trace, all elements that come from XLog library would be dropped.
     *
     * @param stackTrace the full stack trace
     * @return the real stack trace, all elements come from system and library user
     */
    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

}
