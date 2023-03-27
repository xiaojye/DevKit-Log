package com.jye.devkit.log.sample;

import android.app.Application;
import android.content.Context;

import com.jye.devkit.log.DkLog;
import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.format.SimpleFormatter;
import com.jye.devkit.log.printer.DbPrinter;
import com.jye.devkit.log.printer.FilePrinter;
import com.jye.devkit.log.printer.LogPrinter;

/**
 * Created by jye on 2023/3/27.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String logDir = getDir("log", Context.MODE_PRIVATE).getAbsolutePath();
        LogPrinter filePrinter = new FilePrinter(new SimpleFormatter(), logDir);
        LogPrinter dbPrinter = new DbPrinter(this, logDir + "/mylog.db");
        DkLog.init(new LogConfig()
                .setIncludeThread(true)
                .setIncludeStackTrace(true)
                .addPrinter(filePrinter, dbPrinter));
    }
}
