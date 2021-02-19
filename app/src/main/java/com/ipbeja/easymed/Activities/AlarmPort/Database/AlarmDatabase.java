package com.ipbeja.easymed.Activities.AlarmPort.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * The type Alarm reminder db helper.
 */
public class AlarmDatabase extends SQLiteOpenHelper {

    /**
     * The constant DATABASE_NAME.
     */
    private static final String DATABASE_NAME = "alarmreminder.db";

    /**
     * The constant DATABASE_VERSION.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Instantiates a new Alarm reminder db helper.
     *
     * @param context the context
     */
    public AlarmDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * On create.
     *
     * @param sqLiteDatabase the sq lite database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the reminder table
        String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmURIManager.Entry.TABLE_NAME + " ("
                + AlarmURIManager.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmURIManager.Entry.KEY_TITLE + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_DATE + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_TIME + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_REPEAT + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_REPEAT_NO + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_REPEAT_TYPE + " TEXT NOT NULL, "
                + AlarmURIManager.Entry.KEY_ACTIVE + " TEXT NOT NULL " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);


    }

    /**
     * On upgrade.
     *
     * @param sqLiteDatabase the sq lite database
     * @param i              the
     * @param i1             the 1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
