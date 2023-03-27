package com.jye.devkit.log.printer;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;
import com.jye.devkit.log.format.LogFormatter;

/**
 * 日志打印接口
 *
 * @author jye
 * @since 1.0
 */
public abstract class LogPrinter {

    protected final LogFormatter formatter;

    public LogPrinter(LogFormatter formatter) {
        this.formatter = formatter;
    }

    public abstract void print(LogConfig config, LogItem item);
}
