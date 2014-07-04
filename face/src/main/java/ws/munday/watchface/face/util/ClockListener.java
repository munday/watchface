package ws.munday.watchface.face.util;

import java.util.Calendar;

/**
 * An interface to listen to the clock ticking.
 */
public interface ClockListener {
    void onTimeChanged(Calendar calendar);
}
