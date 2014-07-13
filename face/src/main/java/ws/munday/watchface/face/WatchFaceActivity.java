package ws.munday.watchface.face;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import java.util.Calendar;

import ws.munday.watchface.face.util.Clock;
import ws.munday.watchface.face.util.ClockListener;
import ws.munday.watchface.face.util.TimeBroadcastReceiver;

/**
 * Basic watch face example.
 */
public class WatchFaceActivity extends Activity {

    private TextView mTextView;
    private boolean mIsDim;

    private ClockListener mClockListener = new ClockListener() {
        @Override
        public void onTimeChanged(Calendar calendar) {
           updateTime(calendar);
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

        // Set up the UI.
        setContentView(R.layout.activity_watchface);
        mTextView = (TextView) findViewById(R.id.fullscreen_content);

        // Start the clock.
        mClock.start();

        mTimeBroadcastReceiver.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeBroadcastReceiver.unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsDim = true;
        // No need to tick once a second, so stop the clock to save battery.
        mClock.stop();
        updateTime(Calendar.getInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsDim = false;
        // Re-enable the clock to get per second ticks.
        mClock.start();
        updateTime(Calendar.getInstance());
    }

    private void updateTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String color = String.format("#%02d%02d%02d", hour, minute, second);
        String time = String.format("%02d\n%02d\n%02d", hour, minute, second);
        String timeDim = String.format("%02d\n%02d", hour, minute);

        mTextView.setBackgroundColor(Color.parseColor(color));

        // Handle dim state.
        if (mIsDim) {
            mTextView.setText(timeDim);
        } else {
            mTextView.setText(time);
        }
    }
}
