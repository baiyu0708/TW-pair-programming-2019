package util.timer;

import java.time.Duration;

/**
 * 周期性执行任务
 * 可以停止、重启、重设时间间隔
 * <p>
 * 注：线程安全性未测试
 */
public class Repeater {

    private final Runnable task;
    private Duration interval;

    private long startTimeMs;
    private Thread timer;

    public Repeater(Runnable task, Duration interval) {
        this.task = task;
        this.interval = interval;
    }

    public void start(boolean executeImmediately) {
        if (executeImmediately) {
            task.run();
        }
        startTimeMs = System.currentTimeMillis();

        timer = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(interval.toMillis());
                } catch (InterruptedException e) {
                    break;
                }
                task.run();
                startTimeMs = System.currentTimeMillis();
            }
        });
        timer.start();
    }

    public void stop() {
        timer.interrupt();
    }

    public void setInterval(Duration newInterval) {
        timer.interrupt();
        this.interval = newInterval;

        long durationSinceStartMs = System.currentTimeMillis() - startTimeMs;
        long remainingIntervalMs = newInterval.toMillis() - durationSinceStartMs;

        if (remainingIntervalMs > 0) {
            timer = new Thread(() -> {
                try {
                    Thread.sleep(remainingIntervalMs);
                } catch (InterruptedException e) {
                    return;
                }
                startTimeMs = System.currentTimeMillis();

                while (true) {
                    try {
                        Thread.sleep(interval.toMillis());
                    } catch (InterruptedException e) {
                        break;
                    }
                    task.run();
                    startTimeMs = System.currentTimeMillis();
                }
            });
            timer.start();
        } else {
            task.run();
            startTimeMs = System.currentTimeMillis();

            while (true) {
                try {
                    Thread.sleep(interval.toMillis());
                } catch (InterruptedException e) {
                    break;
                }
                task.run();
                startTimeMs = System.currentTimeMillis();
            }
        }
    }
}
