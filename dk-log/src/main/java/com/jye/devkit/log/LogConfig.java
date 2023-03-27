package com.jye.devkit.log;

import com.jye.devkit.log.format.SimpleFormatter;
import com.jye.devkit.log.printer.LogPrinter;
import com.jye.devkit.log.printer.LogcatPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日志配置类
 *
 * @author jye
 * @since 1.0
 */
public class LogConfig {

    private boolean enable = true;
    private String prefix = "";
    private boolean includeThread = false;
    private boolean includeStackTrace = false;
    private int stackTraceDepth = 5;
    private List<LogPrinter> printers = new ArrayList<>();

    public LogConfig() {
        printers.add(new LogcatPrinter(new SimpleFormatter()));
    }

    public LogConfig(LogConfig copy) {
        this.enable = copy.enable;
        this.prefix = copy.prefix;
        this.includeThread = copy.includeThread;
        this.includeStackTrace = copy.includeStackTrace;
        this.stackTraceDepth = copy.stackTraceDepth;
        this.printers = copy.printers;
    }

    public boolean isEnable() {
        return enable;
    }

    public LogConfig setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public LogConfig setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public boolean isIncludeThread() {
        return includeThread;
    }

    public LogConfig setIncludeThread(boolean includeThread) {
        this.includeThread = includeThread;
        return this;
    }

    public boolean isIncludeStackTrace() {
        return includeStackTrace;
    }

    public LogConfig setIncludeStackTrace(boolean includeStackTrace) {
        this.includeStackTrace = includeStackTrace;
        return this;
    }

    public int getStackTraceDepth() {
        return stackTraceDepth;
    }

    public LogConfig setStackTraceDepth(int stackTraceDepth) {
        this.stackTraceDepth = stackTraceDepth;
        return this;
    }

    public List<LogPrinter> getPrinters() {
        return printers;
    }

    public LogConfig addPrinter(LogPrinter... printers) {
        this.printers.addAll(Arrays.asList(printers));
        return this;
    }

}
