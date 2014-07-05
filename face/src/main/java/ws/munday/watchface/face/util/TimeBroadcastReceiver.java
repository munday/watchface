package ws.munday.watchface.face.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Calendar;

public class TimeBroadcastReceiver extends BroadcastReceiver {

    private ClockListener mClockListener;

    public TimeBroadcastReceiver(ClockListener clockListener) {
        mClockListener = clockListener;
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mClockListener.onTimeChanged(Calendar.getInstance());
    }
}
