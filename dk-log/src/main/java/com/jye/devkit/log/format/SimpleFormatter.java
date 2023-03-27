package com.jye.devkit.log.format;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;

/**
 * 简单的格式化实现
 *
 * @author jye
 * @since 1.0
 */
public class SimpleFormatter implements LogFormatter {

    @Override
    public String format(LogConfig config, LogItem item) {

        StringBuilder sb = new StringBuilder();

        //日志内容
        sb.append(item.getContent());

        //线程信息
        if (config.isIncludeThread()) {
            sb.append("\nThread: ").append(String.format("id=%d, name=%s", item.getThread().getId(), item.getThread().getName()));
        }

        //堆栈信息
        if (config.isIncludeStackTrace()) {
            int depth = Math.min(item.getStackTrace().length, config.getStackTraceDepth());
            sb.append("\nStackTrace: ").append(String.format("depth=%d\n", depth));
            for (int i = 0; i < depth; i++) {
                if (i != depth - 1) {
                    sb.append("    ├ ");
                    sb.append(item.getStackTrace()[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("    └ ");
                    sb.append(item.getStackTrace()[i].toString());
                }
            }
        }

        return sb.toString();
    }

}
