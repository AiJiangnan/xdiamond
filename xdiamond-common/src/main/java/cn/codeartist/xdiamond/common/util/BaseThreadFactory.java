package cn.codeartist.xdiamond.common.util;

import lombok.Builder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池工厂
 *
 * @author 艾江南
 * @date 2019/4/14
 */
@Builder
public class BaseThreadFactory implements ThreadFactory {
    /**
     * A counter for the threads created by this factory.
     */
    final private AtomicLong threadCounter = new AtomicLong();
    /**
     * Stores the wrapped factory.
     */
    private ThreadFactory wrappedFactory;
    /**
     * Stores the uncaught exception handler.
     */
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    /**
     * Stores the naming pattern for newly created threads.
     */
    private String namingPattern;
    /**
     * Stores the priority.
     */
    private Integer priority;
    /**
     * Stores the daemon status flag.
     */
    private Boolean daemon;

    @Override
    public Thread newThread(Runnable r) {
        if (wrappedFactory == null) {
            wrappedFactory = Executors.defaultThreadFactory();
        }
        final Thread thread = wrappedFactory.newThread(r);
        if (namingPattern != null) {
            final Long count = threadCounter.incrementAndGet();
            thread.setName(String.format(namingPattern, count));
        }
        if (uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        if (priority != null) {
            thread.setPriority(priority);
        }
        if (daemon != null) {
            thread.setDaemon(daemon);
        }
        return thread;
    }
}
