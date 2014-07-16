package ws.munday.watchface.hexwatch;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

import ws.munday.watchface.face.WatchFaceActivity;

/**
 * Basic watch face example.
 */
public class HexWatchFaceActivity extends WatchFaceActivity {

    private TextView mTextView;
    private boolean mIsDim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the UI.
        setContentView(R.layout.activity_watchface);
        mTextView = (TextView) findViewById(R.id.fullscreen_content);
    }


    @Override
    public void onScreenDimmed() {
        mIsDim = true;
        onTimeTick(Calendar.getInstance());
    }

    @Override
    public void onScreenOn() {
        mIsDim = false;
        onTimeTick(Calendar.getInstance());
    }

    @Override
    public void onTimeTick(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String color = String.format("#%02d%02d%02d", hour, minute, second);
        String time = String.format("%02d\n%02d\n%02d", hour, minute, second);
        String timeDim = String.format("%02d\n%02d", hour, minute);

        mTextView.setBackgroundColor(Color.parseColor(color));

        if (mIsDim) {
            mTextView.setText(timeDim);
        } else {
            mTextView.setText(time);
        }
    }
}
