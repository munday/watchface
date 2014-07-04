package ws.munday.watchfaces.face.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.Calendar;

/**
 * Just a simple clock that ticks every second.
 */

public class Clock {

    private Handler mHandler;
    private ClockListener mListener;

    private Runnable mTickRunnable = new Runnable() {
        @Override
        public void run() {
            mListener.onTimeChanged(Calendar.getInstance());
            long now = SystemClock.uptimeMillis();
            long nextTick = now + (1000 - now % 1000);
            mHandler.postAtTime(this, nextTick);
        }
    };

    public Clock(Looper looper, ClockListener listener) {
        mHandler = new Handler(looper);
        mListener = listener;
    }

    public void start() {
        stop();
        mHandler.post(mTickRunnable);
    }

    public void stop() {
        mHandler.removeCallbacks(mTickRunnable);
    }

}
