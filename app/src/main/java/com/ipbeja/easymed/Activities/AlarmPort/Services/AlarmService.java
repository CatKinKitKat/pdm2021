package com.ipbeja.easymed.Activities.AlarmPort.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.ipbeja.easymed.Activities.AddReminderActivity;
import com.ipbeja.easymed.Activities.AlarmPort.Database.AlarmURIManager;
import com.ipbeja.easymed.R;

/**
 * The type Reminder alarm service.
 */
public class AlarmService extends IntentService {
    /**
     * The constant TAG.
     */
    private static final String TAG = AlarmService.class.getSimpleName();

    /**
     * The constant NOTIFICATION_ID.
     */
    private static final int NOTIFICATION_ID = 42;

    /**
     * The Cursor.
     */
    Cursor cursor;

    /**
     * Instantiates a new Reminder alarm service.
     */
    public AlarmService() {
        super(TAG);
    }

    /**
     * Gets reminder pending intent.
     *
     * @param context the context
     * @param uri     the uri
     * @return the reminder pending intent
     */
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, AlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * On handle intent.
     *
     * @param intent the intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        Intent action = new Intent(this, AddReminderActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if (uri != null) {
            cursor = getContentResolver().query(uri, null, null, null, null);
        }

        String description = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmURIManager.getColumnString(cursor, AlarmURIManager.Entry.KEY_TITLE);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new Notification.Builder(this)
                .setContentTitle(getString(R.string.reminder_title))
                .setContentText(description)
                .setSmallIcon(R.drawable.alarm)
                .setContentIntent(operation)
                .setAutoCancel(true)
                .build();


        manager.notify(NOTIFICATION_ID, note);
    }
}