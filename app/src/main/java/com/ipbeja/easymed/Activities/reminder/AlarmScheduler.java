package com.ipbeja.easymed.Activities.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;


/**
 * The type Alarm scheduler.
 */
public class AlarmScheduler {


    /**
     * Sets alarm.
     *
     * @param context      the context
     * @param alarmTime    the alarm time
     * @param reminderTask the reminder task
     */
    public void setAlarm(Context context, long alarmTime, Uri reminderTask) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context, reminderTask);


        if (Build.VERSION.SDK_INT >= 23) {

            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else if (Build.VERSION.SDK_INT >= 19) {

            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else {

            manager.set(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        }
    }

    /**
     * Sets repeat alarm.
     *
     * @param context      the context
     * @param alarmTime    the alarm time
     * @param reminderTask the reminder task
     * @param RepeatTime   the repeat time
     */
    public void setRepeatAlarm(Context context, long alarmTime, Uri reminderTask, long RepeatTime) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, RepeatTime, operation);


    }

}