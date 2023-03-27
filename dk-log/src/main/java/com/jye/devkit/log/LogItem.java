package com.jye.devkit.log;

/**
 * 日志模型
 *
 * @author jye
 * @since 1.0
 */
public class LogItem {
    private final long timeMillis;
    private final LogType type;
    private final String tag;
    private final String content;
    private final int pid;
    private final int tid;
    private final Thread thread;
    private final StackTraceElement[] stackTrace;

    public LogItem(LogType type, String tag, String content, int pid, int tid, Thread thread, StackTraceElement[] stackTrace) {
        this.timeMillis = System.currentTimeMillis();
        this.type = type;
        this.tag = tag;
        this.content = content;
        this.pid = pid;
        this.tid = tid;
        this.thread = thread;
        this.stackTrace = stackTrace;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public LogType getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public int getPid() {
        return pid;
    }

    public int getTid() {
        return tid;
    }

    public Thread getThread() {
        return thread;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }
}
