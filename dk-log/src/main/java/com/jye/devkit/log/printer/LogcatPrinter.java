package com.jye.devkit.log.printer;

import android.util.Log;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;
import com.jye.devkit.log.format.LogFormatter;

/**
 * 控制台打印
 *
 * @author jye
 * @since 1.0
 */
public class LogcatPrinter extends LogPrinter {
    private static final int MAX_LEN = 512;

    public LogcatPrinter(LogFormatter formatter) {
        super(formatter);
    }

    @Override
    public void print(LogConfig config, LogItem item) {
        int priority;
        switch (item.getType()) {
            default:
            case DEBUG:
                priority = Log.DEBUG;
                break;
            case INFO:
                priority = Log.INFO;
                break;
            case WARN:
                priority = Log.WARN;
                break;
            case ERROR:
                priority = Log.ERROR;
                break;
        }
        String printString = formatter.format(config, item);

        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                log.append(printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                log.append(printString.substring(index, len));
            }
            Log.println(priority, item.getTag(), log.toString());
        } else {
            Log.println(priority, item.getTag(), printString);
        }
    }

}
