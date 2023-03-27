package com.jye.devkit.log;

import android.util.Log;

/**
 * 日志类型
 *
 * @author jye
 * @since 1.0
 */
public enum LogType {
    DEBUG(Log.DEBUG),
    INFO(Log.INFO),
    WARN(Log.WARN),
    ERROR(Log.ERROR);

    private int level;

    LogType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
