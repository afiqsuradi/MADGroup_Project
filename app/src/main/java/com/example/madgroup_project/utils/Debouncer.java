package com.example.madgroup_project.utils;

import android.os.Handler;
import android.os.Looper;

public class Debouncer {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable pendingRunnable;
    private final long delayMilliseconds;

    public Debouncer(long delayMilliseconds) {
        this.delayMilliseconds = delayMilliseconds;
    }

    public void debounce(final Runnable action) {
        cancel();

        pendingRunnable = new Runnable() {
            @Override
            public void run() {
                action.run();
                pendingRunnable = null;
            }
        };

        handler.postDelayed(pendingRunnable, delayMilliseconds);
    }

    public void cancel() {
        if (pendingRunnable != null) {
            handler.removeCallbacks(pendingRunnable);
            pendingRunnable = null;
        }
    }

    public boolean isPending() {
        return pendingRunnable != null;
    }
}