package com.ipbeja.easymed.Activities.AlarmPort.Database;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The type Alarm reminder contract.
 */
public class AlarmURIManager {

    /**
     * The constant CONTENT_AUTHORITY.
     */
    public static final String CONTENT_AUTHORITY = "com.ipbeja.easymed.provider";
    /**
     * The constant BASE_CONTENT_URI.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * The constant PATH_VEHICLE.
     */
    public static final String PATH_VEHICLE = "reminder-path";

    /**
     * Instantiates a new Alarm reminder contract.
     */
    private AlarmURIManager() {
    }

    /**
     * Gets column string.
     *
     * @param cursor     the cursor
     * @param columnName the column name
     * @return the column string
     */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    /**
     * The type Alarm reminder entry.
     */
    public static final class Entry implements BaseColumns {

        /**
         * The constant CONTENT_URI.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEHICLE);

        /**
         * The constant CONTENT_LIST_TYPE.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        /**
         * The constant CONTENT_ITEM_TYPE.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        /**
         * The constant TABLE_NAME.
         */
        public final static String TABLE_NAME = "vehicles";

        /**
         * The constant _ID.
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * The constant KEY_TITLE.
         */
        public static final String KEY_TITLE = "title";
        /**
         * The constant KEY_DATE.
         */
        public static final String KEY_DATE = "date";
        /**
         * The constant KEY_TIME.
         */
        public static final String KEY_TIME = "time";
        /**
         * The constant KEY_REPEAT.
         */
        public static final String KEY_REPEAT = "repeat";
        /**
         * The constant KEY_REPEAT_NO.
         */
        public static final String KEY_REPEAT_NO = "repeat_no";
        /**
         * The constant KEY_REPEAT_TYPE.
         */
        public static final String KEY_REPEAT_TYPE = "repeat_type";
        /**
         * The constant KEY_ACTIVE.
         */
        public static final String KEY_ACTIVE = "active";

    }
}
