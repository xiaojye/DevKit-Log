package com.jye.devkit.log.format;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;

/**
 * 内容格式化接口
 *
 * @author jye
 * @since 1.0
 */
public interface LogFormatter {

    String format(LogConfig config, LogItem item);
}
