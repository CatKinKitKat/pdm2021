package com.ipbeja.easymed.Tools;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.ipbeja.easymed.Activities.ReminderEditActivity;
import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.R;
import com.ipbeja.easymed.SQLite.ReminderDatabase;

import java.util.Calendar;


/**
 * The type Alarm receiver.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    /**
     * The M alarm manager.
     */
    AlarmManager mAlarmManager;
    /**
     * The M pending intent.
     */
    PendingIntent mPendingIntent;

    /**
     * On receive.
     *
     * @param context the context
     * @param intent  the intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        int mReceivedID = Integer.parseInt(intent.getStringExtra(ReminderEditActivity.EXTRA_REMINDER_ID));

        ReminderDatabase rb = new ReminderDatabase(context);
        Reminder reminder = rb.getReminder(mReceivedID);
        String mTitle = reminder.getTitle();

        Intent editIntent = new Intent(context, ReminderEditActivity.class);
        editIntent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
        PendingIntent mClick = PendingIntent.getActivity(context, mReceivedID, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setTicker(mTitle)
                .setContentText(mTitle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(mClick)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(mReceivedID, mBuilder.build());
    }

    /**
     * Sets alarm.
     *
     * @param context  the context
     * @param calendar the calendar
     * @param ID       the id
     */
    public void setAlarm(Context context, Calendar calendar, int ID) {

        this.mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
        this.mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        this.mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                this.mPendingIntent);


        ComponentName receiver = new ComponentName(context, BootStateBroadcast.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * Sets repeat alarm.
     *
     * @param context    the context
     * @param calendar   the calendar
     * @param ID         the id
     * @param RepeatTime the repeat time
     */
    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {

        this.mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
        this.mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        this.mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                RepeatTime, this.mPendingIntent);

        context.getPackageManager().setComponentEnabledSetting(
                new ComponentName(context, BootStateBroadcast.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    /**
     * Cancel alarm.
     *
     * @param context the context
     * @param ID      the id
     */
    public void cancelAlarm(Context context, int ID) {

        this.mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        this.mPendingIntent = PendingIntent.getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
        this.mAlarmManager.cancel(this.mPendingIntent);

        context.getPackageManager().setComponentEnabledSetting(
                new ComponentName(context, BootStateBroadcast.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}