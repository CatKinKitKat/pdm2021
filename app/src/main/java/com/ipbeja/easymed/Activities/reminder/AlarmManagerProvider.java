package com.ipbeja.easymed.Activities.reminder;

import android.app.AlarmManager;
import android.content.Context;


/**
 * The type Alarm manager provider.
 */
public class AlarmManagerProvider {
    /**
     * The constant TAG.
     */
    private static final String TAG = AlarmManagerProvider.class.getSimpleName();
    /**
     * The constant sAlarmManager.
     */
    private static AlarmManager sAlarmManager;

    /**
     * Gets alarm manager.
     *
     * @param context the context
     * @return the alarm manager
     */
    static synchronized AlarmManager getAlarmManager(Context context) {
        if (sAlarmManager == null) {
            sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return sAlarmManager;
    }
}
