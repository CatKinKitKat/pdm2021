package com.ipbeja.easymed.Alarm.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ipbeja.easymed.Objects.Reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Reminder database.
 */
public class ReminderDatabase extends SQLiteOpenHelper {

    /**
     * The constant DATABASE_VERSION.
     */
    private static final int DATABASE_VERSION = 1;


    /**
     * The constant DATABASE_NAME.
     */
    private static final String DATABASE_NAME = "ReminderDatabase";


    /**
     * The constant TABLE_REMINDERS.
     */
    private static final String TABLE_REMINDERS = "ReminderTable";


    /**
     * The constant KEY_ID.
     */
    private static final String KEY_ID = "id";
    /**
     * The constant KEY_TITLE.
     */
    private static final String KEY_TITLE = "title";
    /**
     * The constant KEY_DATE.
     */
    private static final String KEY_DATE = "date";
    /**
     * The constant KEY_TIME.
     */
    private static final String KEY_TIME = "time";
    /**
     * The constant KEY_REPEAT.
     */
    private static final String KEY_REPEAT = "repeat";
    /**
     * The constant KEY_REPEAT_NO.
     */
    private static final String KEY_REPEAT_NO = "repeat_no";
    /**
     * The constant KEY_REPEAT_TYPE.
     */
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    /**
     * The constant KEY_ACTIVE.
     */
    private static final String KEY_ACTIVE = "active";

    /**
     * Instantiates a new Reminder database.
     *
     * @param context the context
     */
    public ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * On create.
     *
     * @param db the db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT + " BOOLEAN,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_REPEAT_TYPE + " TEXT,"
                + KEY_ACTIVE + " BOOLEAN" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }


    /**
     * On upgrade.
     *
     * @param db         the db
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);


        onCreate(db);
    }


    /**
     * Add reminder int.
     *
     * @param reminder the reminder
     * @return the int
     */
    public int addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DATE, reminder.getDate());
        values.put(KEY_TIME, reminder.getTime());
        values.put(KEY_REPEAT, reminder.getRepeat());
        values.put(KEY_REPEAT_NO, reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());


        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }


    /**
     * Gets reminder.
     *
     * @param id the id
     * @return the reminder
     */
    public Reminder getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_REPEAT_NO,
                                KEY_REPEAT_TYPE,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return reminder;
    }


    /**
     * Gets all reminders.
     *
     * @return the all reminders
     */
    public List<Reminder> getAllReminders() {
        List<Reminder> reminderList = new ArrayList<>();


        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setID(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDate(cursor.getString(2));
                reminder.setTime(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4));
                reminder.setRepeatNo(cursor.getString(5));
                reminder.setRepeatType(cursor.getString(6));
                reminder.setActive(cursor.getString(7));


                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }


    /**
     * Gets reminders count.
     *
     * @return the reminders count
     */
    public int getRemindersCount() {
        String countQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


    /**
     * Update reminder int.
     *
     * @param reminder the reminder
     * @return the int
     */
    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DATE, reminder.getDate());
        values.put(KEY_TIME, reminder.getTime());
        values.put(KEY_REPEAT, reminder.getRepeat());
        values.put(KEY_REPEAT_NO, reminder.getRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(KEY_ACTIVE, reminder.getActive());


        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
    }


    /**
     * Delete reminder.
     *
     * @param reminder the reminder
     */
    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
        db.close();
    }
}

