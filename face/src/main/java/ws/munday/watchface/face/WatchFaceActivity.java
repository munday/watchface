package ws.munday.watchface.face;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;

import java.util.Calendar;

import ws.munday.watchface.face.util.Clock;
import ws.munday.watchface.face.util.ClockListener;

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

    @Override
    protected void onPause() {
        super.onPause();
        mIsDim = true;
        updateTime(Calendar.getInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsDim = false;
        updateTime(Calendar.getInstance());
    }

    private void updateTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String time = String.format("#%02d%02d%02d", hour, minute, second);
        String timeDim = String.format("%02d\n%02d\n%02d", hour, minute, second);

        mTextView.setBackgroundColor(Color.parseColor(time));

        // Handle dim state.
        if (mIsDim) {
            // Cards are no longer displayed, move the time to the center.
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setTypeface(null, Typeface.BOLD);
            mTextView.setLineSpacing(0.0f,0.7f);
            mTextView.setTextSize(55);
            mTextView.setText(timeDim);
        } else {
            // Move the time out of the way of any cards.
            mTextView.setGravity(Gravity.TOP | Gravity.RIGHT);
            mTextView.setTypeface(null, Typeface.NORMAL);
            mTextView.setLineSpacing(0.0f,1f);
            mTextView.setTextSize(25);
            mTextView.setText(time);
        }
    }
}
