package util.timer;

import java.time.Duration;
import java.util.TimerTask;

/**
 * 周期性执行任务
 * 可以停止、重启、重设时间间隔
 * <p>
 * 注：线程安全性未测试
 */
public class Repeater {

    private final Runnable task;
    private Duration interval;

    private java.util.Timer timer;
    private long latestRunTimeMs;

    private boolean running = false;

    public Repeater(Runnable task, Duration interval) {
        this.task = task;
        this.interval = interval;
    }

    public void start(boolean executeImmediately) {
        running = true;
        timer = new java.util.Timer();

        if (executeImmediately) {
            Repeater.this.run();
        }
        delayExecute(interval);
    }

    public void stop() {
        running = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, 0);
    }

    public void setInterval(Duration newInterval) {
        this.interval = newInterval;

        if (running) {
            stop();
            timer = new java.util.Timer();

            long durationMs = System.currentTimeMillis() - latestRunTimeMs;
            if (durationMs < newInterval.toMillis()) {
                delayExecute(Duration.ofMillis(newInterval.toMillis() - durationMs), newInterval);
            } else {
                run();
                delayExecute(newInterval);
            }
        }
    }

    private void delayExecute(Duration delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Repeater.this.run();
                delayExecute(delay);
            }
        }, delay.toMillis());
    }

    private void delayExecute(Duration firstDelay, Duration followingDelay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Repeater.this.run();
                delayExecute(followingDelay);
            }
        }, firstDelay.toMillis());
    }

    private void run() {
        latestRunTimeMs = System.currentTimeMillis();
        task.run();
    }
}
