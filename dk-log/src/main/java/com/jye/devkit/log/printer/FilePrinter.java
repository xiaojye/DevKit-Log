package com.jye.devkit.log.printer;

import android.os.AsyncTask;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;
import com.jye.devkit.log.format.LogFormatter;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 文件打印
 *
 * @author jye
 * @since 1.0
 */
public class FilePrinter extends LogPrinter {
    private static final DateFormat FILE_NAME_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final String mDirPath;

    public FilePrinter(LogFormatter formatter, String dirPath) {
        super(formatter);
        this.mDirPath = dirPath;
    }

    @Override
    public void print(LogConfig config, LogItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String fileName = FILE_NAME_FORMAT.format(item.getTimeMillis()) + ".log";
                File file = new File(mDirPath, fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

//        2023-03-27 20:05:57.308 pid-tid $LEVEL $TAG: $内容
                String time = TIME_FORMAT.format(item.getTimeMillis());
                int pid = item.getPid();
                long tid = item.getTid();
                String levelStr;
                switch (item.getType()) {
                    default:
                    case DEBUG:
                        levelStr = "D";
                        break;
                    case INFO:
                        levelStr = "I";
                        break;
                    case WARN:
                        levelStr = "W";
                        break;
                    case ERROR:
                        levelStr = "E";
                        break;
                }
                String prefix = String.format("%s\t%d-%d\t%s\t%s: ", time, pid, tid, levelStr, item.getTag());
                String printString = formatter.format(config, item);
                if (printString.contains("\n")) {
                    String[] split = printString.split("\n");
                    String spaceWidgetByPrefix = getSpaceWidgetByStr(prefix);
                    for (int i = 0; i < split.length; i++) {
                        String s = split[i];
                        if (i == 0) {
                            appendWriteToFile(file, prefix + s + "\n");
                        } else {
                            appendWriteToFile(file, spaceWidgetByPrefix + s + "\n");
                        }
                    }
                } else {
                    appendWriteToFile(file, prefix + "\t" + printString + "\n");
                }
            }
        });
    }

    private String getSpaceWidgetByStr(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 将字符串追加写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    private static boolean appendWriteToFile(final File file, final String content) {
        if (file == null || content == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(bw);
        }
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    private static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
