package com.ipbeja.easymed.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.SQLite.ReminderDatabase;

import java.util.Calendar;
import java.util.List;


/**
 * The type Boot receiver.
 */
public class BootReceiver extends BroadcastReceiver {

    /**
     * The constant milMinute.
     */
    private static final long milMinute = 60000L;
    /**
     * The constant milHour.
     */
    private static final long milHour = 3600000L;
    /**
     * The constant milDay.
     */
    private static final long milDay = 86400000L;
    /**
     * The constant milWeek.
     */
    private static final long milWeek = 604800000L;
    /**
     * The constant milMonth.
     */
    private static final long milMonth = 2592000000L;
    /**
     * The M repeat time.
     */
    private long mRepeatTime;

    /**
     * On receive.
     *
     * @param context the context
     * @param intent  the intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            ReminderDatabase rb = new ReminderDatabase(context);
            Calendar mCalendar = Calendar.getInstance();
            AlarmReceiver mAlarmReceiver = new AlarmReceiver();

            List<Reminder> reminders = rb.getAllReminders();

            for (Reminder rm : reminders) {
                int mReceivedID = rm.getID();
                String mRepeat = rm.getRepeat();
                String mRepeatNo = rm.getRepeatNo();
                String mRepeatType = rm.getRepeatType();
                String mActive = rm.getActive();
                String mDate = rm.getDate();
                String mTime = rm.getTime();

                String[] mDateSplit = mDate.split("/");
                String[] mTimeSplit = mTime.split(":");

                int mDay = Integer.parseInt(mDateSplit[0]);
                int mMonth = Integer.parseInt(mDateSplit[1]);
                int mYear = Integer.parseInt(mDateSplit[2]);
                int mHour = Integer.parseInt(mTimeSplit[0]);
                int mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                switch (mRepeatType) {
                    case "Minute":
                        this.mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                        break;
                    case "Hour":
                        this.mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                        break;
                    case "Day":
                        this.mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                        break;
                    case "Week":
                        this.mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                        break;
                    case "Month":
                        this.mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                        break;
                }

                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, this.mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }
        }
    }
}