package ws.munday.watchfaces.face;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Calendar;

import ws.munday.watchfaces.face.util.Clock;
import ws.munday.watchfaces.face.util.ClockListener;

/**
 * Basic watch face example.
 */
public class WatchFaceActivity extends Activity {

    private TextView mTextView;

    private ClockListener mClockListener = new ClockListener() {
        @Override
        public void onTimeChanged(Calendar calendar) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            String timeHex = String.format("#%02d%02d%02d", hour, minute, second);

            mTextView.setBackgroundColor(Color.parseColor(timeHex));
            mTextView.setText(timeHex);
        }
    };

    private Clock mClock = new Clock(Looper.getMainLooper(), mClockListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the UI.
        setContentView(R.layout.activity_watchface);
        mTextView = (TextView) findViewById(R.id.fullscreen_content);

        // Start the clock.
        mClock.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClock.stop();
    }

    /**
     * Handle watch face dimming here.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Cards are no longer displayed, move the time to the center.
        mTextView.setGravity(Gravity.CENTER);
    }

    /**
     * Dim state ends here.
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Move the time out of the way of any cards.
        mTextView.setGravity(Gravity.TOP | Gravity.RIGHT);
    }
}
