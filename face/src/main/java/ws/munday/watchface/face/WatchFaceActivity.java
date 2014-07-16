package ws.munday.watchface.face;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;

import java.util.Calendar;

import ws.munday.watchface.face.util.Clock;
import ws.munday.watchface.face.util.ClockListener;
import ws.munday.watchface.face.util.TimeBroadcastReceiver;

/**
 * Basic watch face example.
 */
public abstract class WatchFaceActivity extends Activity {

    private DisplayManager mDisplayManager;
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {

        }

        @Override
        public void onDisplayRemoved(int displayId) {

        }

        @Override
        public void onDisplayChanged(int displayId) {
            Display display = mDisplayManager.getDisplay(displayId);
            switch (display.getState()) {
                case Display.STATE_DOZING:
                    mClock.stop();
                    onScreenDimmed();
                    break;
                case Display.STATE_ON:
                    mClock.start();
                    onScreenOn();
                    break;
            }
        }
    };

    private ClockListener mClockListener = new ClockListener() {
        @Override
        public void onTimeChanged(Calendar calendar) {
           onTimeTick(calendar);
        }
    };

    // The TimeBroadcastReceiver handles ticks when the ui would otherwise be disabled.
    // Which is when the watch is dimmed. Unfortunately, the events are fired only once a minute.
    private TimeBroadcastReceiver mTimeBroadcastReceiver = new TimeBroadcastReceiver(mClockListener);

    // The Clock ticks once a second, but will not update the ui in the dimmed state.
    private Clock mClock = new Clock(Looper.getMainLooper(), mClockListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClock.start();
        mDisplayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        mDisplayManager.registerDisplayListener(mDisplayListener, new Handler());
        mTimeBroadcastReceiver.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisplayManager.unregisterDisplayListener(mDisplayListener);
        mTimeBroadcastReceiver.unregister(this);
    }

    public abstract void onScreenDimmed();

    public abstract void onScreenOn();

    public abstract void onTimeTick(Calendar calendar);

}
