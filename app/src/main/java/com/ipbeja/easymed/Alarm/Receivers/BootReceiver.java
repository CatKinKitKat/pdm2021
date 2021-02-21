package com.ipbeja.easymed.Alarm.Receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ipbeja.easymed.Alarm.DataBase.ReminderDatabase;
import com.ipbeja.easymed.Objects.Reminder;

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
     * The M title.
     */
    private String mTitle;
    /**
     * The M time.
     */
    private String mTime;
    /**
     * The M date.
     */
    private String mDate;
    /**
     * The M repeat no.
     */
    private String mRepeatNo;
    /**
     * The M repeat type.
     */
    private String mRepeatType;
    /**
     * The M active.
     */
    private String mActive;
    /**
     * The M repeat.
     */
    private String mRepeat;
    /**
     * The M date split.
     */
    private String[] mDateSplit;
    /**
     * The M time split.
     */
    private String[] mTimeSplit;
    /**
     * The M year.
     */
    private int mYear, /**
     * The M month.
     */
    mMonth, /**
     * The M hour.
     */
    mHour, /**
     * The M minute.
     */
    mMinute, /**
     * The M day.
     */
    mDay, /**
     * The M received id.
     */
    mReceivedID;
    /**
     * The M repeat time.
     */
    private long mRepeatTime;
    /**
     * The M calendar.
     */
    private Calendar mCalendar;
    /**
     * The M alarm receiver.
     */
    private AlarmReceiver mAlarmReceiver;

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
            mCalendar = Calendar.getInstance();
            mAlarmReceiver = new AlarmReceiver();

            List<Reminder> reminders = rb.getAllReminders();

            for (Reminder rm : reminders) {
                mReceivedID = rm.getID();
                mRepeat = rm.getRepeat();
                mRepeatNo = rm.getRepeatNo();
                mRepeatType = rm.getRepeatType();
                mActive = rm.getActive();
                mDate = rm.getDate();
                mTime = rm.getTime();

                mDateSplit = mDate.split("/");
                mTimeSplit = mTime.split(":");

                mDay = Integer.parseInt(mDateSplit[0]);
                mMonth = Integer.parseInt(mDateSplit[1]);
                mYear = Integer.parseInt(mDateSplit[2]);
                mHour = Integer.parseInt(mTimeSplit[0]);
                mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);


                switch (mRepeatType) {
                    case "Minute":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                        break;
                    case "Hour":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                        break;
                    case "Day":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                        break;
                    case "Week":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                        break;
                    case "Month":
                        mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                        break;
                }


                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }
        }
    }
}