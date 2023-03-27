package com.jye.devkit.log.printer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.jye.devkit.log.LogConfig;
import com.jye.devkit.log.LogItem;
import com.jye.devkit.log.LogType;
import com.jye.devkit.log.format.LogFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 数据库打印
 *
 * @author jye
 * @since 1.0
 */
public class DbPrinter extends LogPrinter {
    private static final String TABLE_NAME = "log_record";
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final MyDbHelper mDbHelper;

    public DbPrinter(Context context, String dbName) {
        super(null);
        mDbHelper = new MyDbHelper(context, dbName, null, 1);
    }

    @Override
    public void print(LogConfig config, LogItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
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

                StringBuilder stackTraceSb = new StringBuilder();
                for (int i = 0, len = item.getStackTrace().length; i < len; i++) {
                    stackTraceSb.append(item.getStackTrace()[i].toString());
                    if (i != len - 1) {
                        stackTraceSb.append("\n");
                    }
                }

                ContentValues values = new ContentValues();
                values.put("time", TIME_FORMAT.format(item.getTimeMillis()));
                values.put("pid", item.getPid());
                values.put("tid", item.getTid());
                values.put("level", levelStr);
                values.put("tag", item.getTag());
                values.put("content", item.getContent());
                values.put("thread", item.getThread().getId() + "," + item.getThread().getName());
                values.put("stackTrace", stackTraceSb.toString());
                mDbHelper.insert(TABLE_NAME, values);
            }
        });
    }

    private static class MyDbHelper extends SQLiteOpenHelper {

        private final SQLiteDatabase database;

        public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            database = getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "time TEXT, "
                    + "pid TEXT,"
                    + "tid TEXT,"
                    + "level TEXT,"
                    + "tag TEXT,"
                    + "content TEXT,"
                    + "thread TEXT,"
                    + "stackTrace TEXT"
                    + ")"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void insert(String tableName, ContentValues values) {
            database.insert(tableName, null, values);
        }
    }

}
