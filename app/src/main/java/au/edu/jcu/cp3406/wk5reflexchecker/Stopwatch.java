package au.edu.jcu.cp3406.wk5reflexchecker;

import java.util.Locale;

public class Stopwatch {
    private long startTime = 0;
    private boolean running = false;
    private long elapsed = 0;
    private long secs = 0;
    private long mins = 0;

    void start() {
        startTime = System.nanoTime();
        running = true;
    }

    void stop() {
        elapsed = System.nanoTime();
        running = false;
    }

    void tick() {
        secs = ((System.nanoTime() - startTime) / 1000000000) % 60;
        mins = (((System.nanoTime() - startTime) / 1000000000) / 60) % 60;
    }

    boolean isRunning() { return running; }

    long getElapsed() { return elapsed; }

    public String toString() {
        return String.format(Locale.getDefault(),"%02d",mins) + ":"
                + String.format(Locale.getDefault(),"%02d",secs);
    }
}
