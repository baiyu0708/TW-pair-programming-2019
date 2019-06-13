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

    private boolean running = false;

    public Repeater(Runnable task, Duration interval) {
        this.task = task;
        this.interval = interval;
    }

    public void start(boolean executeImmediately) {
        running = true;

        if (executeImmediately) {
            task.run();
        }

        timer = new Thread(this::loopUntilInterrupted);
        timer.start();
    }

    public void stop() {
        if (!running) {
            return;
        }

        running = false;

        timer.interrupt();
    }

    public void setInterval(Duration newInterval) {
        this.interval = newInterval;
        if (!running) {
            return;
        }

        timer.interrupt();

        long durationSinceStartMs = System.currentTimeMillis() - startTimeMs;
        long remainingIntervalMs = newInterval.toMillis() - durationSinceStartMs;

        if (remainingIntervalMs > 0) {
            timer = new Thread(() -> {
                try {
                    Thread.sleep(remainingIntervalMs);
                } catch (InterruptedException e) {
                    return;
                }
                task.run();
                loopUntilInterrupted();
            });
            timer.start();
        } else {
            task.run();
            timer = new Thread(this::loopUntilInterrupted);
            timer.start();
        }
    }

    private void loopUntilInterrupted() {
        while (true) {
            startTimeMs = System.currentTimeMillis();
            try {
                Thread.sleep(interval.toMillis());
            } catch (InterruptedException e) {
                break;
            }
            task.run();
        }
    }
}
