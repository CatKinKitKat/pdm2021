package com.ipbeja.easymed.Activities;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.Activities.data.AlarmReminderContract;
import com.ipbeja.easymed.Activities.reminder.AlarmScheduler;
import com.ipbeja.easymed.R;

import java.util.Calendar;

/**
 * The type Add reminder activity.
 */
public class AddReminderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The constant EXISTING_VEHICLE_LOADER.
     */
    private static final int EXISTING_VEHICLE_LOADER = 0;

    /**
     * The constant KEY_TITLE.
     */
    private static final String KEY_TITLE = "title_key";
    /**
     * The constant KEY_TIME.
     */
    private static final String KEY_TIME = "time_key";
    /**
     * The constant KEY_DATE.
     */
    private static final String KEY_DATE = "date_key";
    /**
     * The constant KEY_REPEAT.
     */
    private static final String KEY_REPEAT = "repeat_key";
    /**
     * The constant KEY_REPEAT_NO.
     */
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    /**
     * The constant KEY_REPEAT_TYPE.
     */
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    /**
     * The constant KEY_ACTIVE.
     */
    private static final String KEY_ACTIVE = "active_key";

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
     * The M title text.
     */
    private EditText mTitleText;
    /**
     * The M date text.
     */
    private TextView mDateText, /**
     * The M time text.
     */
    mTimeText, /**
     * The M repeat text.
     */
    mRepeatText, /**
     * The M repeat no text.
     */
    mRepeatNoText, /**
     * The M repeat type text.
     */
    mRepeatTypeText;
    /**
     * The M fab 1.
     */
    private FloatingActionButton mFAB1;
    /**
     * The M fab 2.
     */
    private FloatingActionButton mFAB2;
    /**
     * The M calendar.
     */
    private Calendar mCalendar;
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
    mDay;
    /**
     * The M repeat time.
     */
    private long mRepeatTime;
    /**
     * The M repeat switch.
     */
    private SwitchCompat mRepeatSwitch;
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
     * The M repeat.
     */
    private String mRepeat;
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
     * The M current reminder uri.
     */
    private Uri mCurrentReminderUri;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();

        if (mCurrentReminderUri == null) {

            setTitle(getString(R.string.add_reminder));


            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.edit_reminder_title));


            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, this);
        }


        mTitleText = findViewById(R.id.reminder_title);
        mDateText = findViewById(R.id.set_date);
        mTimeText = findViewById(R.id.set_time);
        mRepeatText = findViewById(R.id.set_repeat);
        mRepeatNoText = findViewById(R.id.set_repeat_no);
        mRepeatTypeText = findViewById(R.id.set_repeat_type);
        mRepeatSwitch = findViewById(R.id.repeat_switch);
        mFAB1 = findViewById(R.id.starred1);
        mFAB2 = findViewById(R.id.starred2);


        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;


        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");


        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(savedRepeatNo);
            mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }


        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_reminder));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /**
     * On save instance state.
     *
     * @param outState the out state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitleText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
    }

    /**
     * Sets time.
     *
     * @param v the v
     */
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();
        this.mHour = now.get(Calendar.HOUR_OF_DAY);
        this.mMinute = now.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(
                this, (view, hourOfDay, minute) -> {
            this.mHour = hourOfDay;
            this.mMinute = minute;
            if (minute < 10) {
                this.mTime = hourOfDay + ":" + "0" + minute;
            } else {
                this.mTime = hourOfDay + ":" + minute;
            }
            this.mTimeText.setText(this.mTime);
        }, this.mHour, this.mMinute, false);
        tpd.show();
    }

    /**
     * Sets date.
     *
     * @param v the v
     */
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        now.get(Calendar.YEAR);
        now.get(Calendar.MONTH);
        now.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(
                this, (view, year, month, dayOfMonth) -> {
            this.mMonth++;
            this.mDay = dayOfMonth;
            this.mMonth = month;
            this.mYear = year;
            this.mDate = dayOfMonth + "/" + month + "/" + year;
            this.mDateText.setText(this.mDate);
        }, this.mYear, this.mMonth, this.mDay);
        dpd.show();
    }


    /**
     * Select fab 1.
     *
     * @param v the v
     */
    public void selectFab1(View v) {
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }


    /**
     * Select fab 2.
     *
     * @param v the v
     */
    public void selectFab2(View v) {
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }


    /**
     * On switch repeat.
     *
     * @param view the view
     */
    public void onSwitchRepeat(View view) {
        boolean on = ((SwitchCompat) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            mRepeatText.setText(R.string.repeat_off);
        }
    }


    /**
     * Select repeat type.
     *
     * @param v the v
     */
    public void selectRepeatType(View v) {
        final String[] items = new String[5];

        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, (dialog, item) -> {

            mRepeatType = items[item];
            mRepeatTypeText.setText(mRepeatType);
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * Sets repeat no.
     *
     * @param v the v
     */
    public void setRepeatNo(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        } else {
                            mRepeatNo = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }

    /**
     * On create options menu boolean.
     *
     * @param menu the menu
     * @return the boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     *
     * @param menu the menu
     * @return the boolean
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mCurrentReminderUri == null) {
            MenuItem menuItem = menu.findItem(R.id.discard_reminder);
            menuItem.setVisible(false);
        }
        return true;
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.save_reminder:


                if (mTitleText.getText().toString().length() == 0) {
                    mTitleText.setError("Reminder Title cannot be blank!");
                } else {
                    saveReminder();
                    finish();
                }
                return true;

            case R.id.discard_reminder:
                showDeleteConfirmationDialog();
                return true;

            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Show unsaved changes dialog.
     *
     * @param discardButtonClickListener the discard button click listener
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_prompt);
        builder.setPositiveButton(R.string.yes, discardButtonClickListener);
        builder.setNegativeButton(R.string.no, (dialog, id) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Show delete confirmation dialog.
     */
    private void showDeleteConfirmationDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_prompt);
        builder.setPositiveButton(R.string.yes, (dialog, id) -> {

            deleteReminder();
        });
        builder.setNegativeButton(R.string.no, (dialog, id) -> {


            if (dialog != null) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Delete reminder.
     */
    private void deleteReminder() {

        if (mCurrentReminderUri != null) {


            int rowsDeleted = getContentResolver().delete(mCurrentReminderUri, null, null);


            if (rowsDeleted == 0) {

                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Success",
                        Toast.LENGTH_SHORT).show();
            }
        }


        finish();
    }


    /**
     * Save reminder.
     */
    public void saveReminder() {

        ContentValues values = new ContentValues();

        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE, mTitle);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_DATE, mDate);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_TIME, mTime);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT, mRepeat);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO, mRepeatNo);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE, mRepeatType);
        values.put(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE, mActive);


        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        long selectedTimestamp = mCalendar.getTimeInMillis();


        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }

        if (mCurrentReminderUri == null) {


            Uri newUri = getContentResolver().insert(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, values);


            if (newUri == null) {

                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Success",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentReminderUri, values, null, null);


            if (rowsAffected == 0) {

                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Success",
                        Toast.LENGTH_SHORT).show();
            }
        }


        if (mActive.equals("true")) {
            if (mRepeat.equals("true")) {
                new AlarmScheduler().setRepeatAlarm(getApplicationContext(), selectedTimestamp, mCurrentReminderUri, mRepeatTime);
            } else if (mRepeat.equals("false")) {
                new AlarmScheduler().setAlarm(getApplicationContext(), selectedTimestamp, mCurrentReminderUri);
            }

            Toast.makeText(this, "Alarm time is " + selectedTimestamp,
                    Toast.LENGTH_LONG).show();
        }


        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }


    /**
     * On back pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    /**
     * On create loader loader.
     *
     * @param i      the
     * @param bundle the bundle
     * @return the loader
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AlarmReminderContract.AlarmReminderEntry._ID,
                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE,
        };


        return new CursorLoader(this,
                mCurrentReminderUri,
                projection,
                null,
                null,
                null);
    }

    /**
     * On load finished.
     *
     * @param loader the loader
     * @param cursor the cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }


        if (cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
            int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
            int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
            int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
            int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
            int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);


            String title = cursor.getString(titleColumnIndex);
            String date = cursor.getString(dateColumnIndex);
            String time = cursor.getString(timeColumnIndex);
            String repeat = cursor.getString(repeatColumnIndex);
            String repeatNo = cursor.getString(repeatNoColumnIndex);
            String repeatType = cursor.getString(repeatTypeColumnIndex);
            String active = cursor.getString(activeColumnIndex);


            mTitleText.setText(title);
            mDateText.setText(date);
            mTimeText.setText(time);
            mRepeatNoText.setText(repeatNo);
            mRepeatTypeText.setText(repeatType);
            mRepeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");


            if (repeat.equals("false")) {
                mRepeatSwitch.setChecked(false);
                mRepeatText.setText(R.string.repeat_off);

            } else if (repeat.equals("true")) {
                mRepeatSwitch.setChecked(true);
            }

        }


    }

    /**
     * On loader reset.
     *
     * @param loader the loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
