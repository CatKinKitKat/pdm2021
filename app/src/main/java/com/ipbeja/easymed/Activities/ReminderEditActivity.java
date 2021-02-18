package com.ipbeja.easymed.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.R;
import com.ipbeja.easymed.SQLite.ReminderDatabase;
import com.ipbeja.easymed.Tools.AlarmReceiver;

import java.util.Calendar;


/**
 * The type Reminder edit activity.
 */
public class ReminderEditActivity extends AppCompatActivity {

    /**
     * The constant EXTRA_REMINDER_ID.
     */
    public static final String EXTRA_REMINDER_ID = "Reminder_ID";
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
     * The M received id.
     */
    private int mReceivedID;
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
     * The M calendar.
     */
    private Calendar mCalendar;
    /**
     * The M received reminder.
     */
    private Reminder mReceivedReminder;
    /**
     * The Rb.
     */
    private ReminderDatabase rb;
    /**
     * The M alarm receiver.
     */
    private AlarmReceiver mAlarmReceiver;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.reminder_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mTitleText = findViewById(R.id.reminder_title);
        this.mDateText = findViewById(R.id.set_date);
        this.mTimeText = findViewById(R.id.set_time);
        this.mRepeatText = findViewById(R.id.set_repeat);
        this.mRepeatNoText = findViewById(R.id.set_repeat_no);
        this.mRepeatTypeText = findViewById(R.id.set_repeat_type);
        SwitchCompat mRepeatSwitch = findViewById(R.id.repeat_switch);

        this.mTitleText.addTextChangedListener(new TextWatcher() {
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

        this.mReceivedID = Integer.parseInt(getIntent().getStringExtra(EXTRA_REMINDER_ID));

        this.rb = new ReminderDatabase(this);
        this.mReceivedReminder = rb.getReminder(mReceivedID);

        this.mTitle = this.mReceivedReminder.getTitle();
        this.mDate = this.mReceivedReminder.getDate();
        this.mTime = this.mReceivedReminder.getTime();
        this.mRepeat = this.mReceivedReminder.getRepeat();
        this.mRepeatNo = this.mReceivedReminder.getRepeatNo();
        this.mRepeatType = this.mReceivedReminder.getRepeatType();
        this.mActive = this.mReceivedReminder.getActive();

        this.mTitleText.setText(mTitle);
        this.mDateText.setText(mDate);
        this.mTimeText.setText(mTime);
        this.mRepeatNoText.setText(mRepeatNo);
        this.mRepeatTypeText.setText(mRepeatType);
        this.mRepeatText.setText("Every " + this.mRepeatNo + " " + this.mRepeatType + "(s)");

        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            this.mTitleText.setText(savedTitle);
            this.mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            this.mTimeText.setText(savedTime);
            this.mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            this.mDateText.setText(savedDate);
            this.mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            this.mRepeatText.setText(saveRepeat);
            this.mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            this.mRepeatNoText.setText(savedRepeatNo);
            this.mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            this.mRepeatTypeText.setText(savedRepeatType);
            this.mRepeatType = savedRepeatType;

            this.mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

        if (mRepeat.equals("false")) {

            mRepeatSwitch.setChecked(false);
            this.mRepeatText.setText(R.string.repeat_off);
        } else if (mRepeat.equals("true")) {

            mRepeatSwitch.setChecked(true);
        }

        this.mCalendar = Calendar.getInstance();
        this.mAlarmReceiver = new AlarmReceiver();

        String[] mDateSplit = this.mDate.split("/");
        String[] mTimeSplit = this.mTime.split(":");

        this.mDay = Integer.parseInt(mDateSplit[0]);
        this.mMonth = Integer.parseInt(mDateSplit[1]);
        this.mYear = Integer.parseInt(mDateSplit[2]);
        this.mHour = Integer.parseInt(mTimeSplit[0]);
        this.mMinute = Integer.parseInt(mTimeSplit[1]);
    }


    /**
     * On save instance state.
     *
     * @param outState the out state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, this.mTitleText.getText());
        outState.putCharSequence(KEY_TIME, this.mTimeText.getText());
        outState.putCharSequence(KEY_DATE, this.mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, this.mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, this.mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, this.mRepeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE, this.mActive);
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
            this.mTimeText.setText(mTime);
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
        this.mYear = now.get(Calendar.YEAR);
        this.mMonth = now.get(Calendar.MONTH);
        this.mDay = now.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(
                this, (view, year, month, dayOfMonth) -> {
            month++;
            this.mDay = dayOfMonth;
            this.mMonth = month;
            this.mYear = year;
            this.mDate = dayOfMonth + "/" + month + "/" + year;
            this.mDateText.setText(this.mDate);
        }, this.mYear, this.mMonth, this.mDay);

        dpd.show();
    }

    /**
     * On switch repeat.
     *
     * @param switchCompat the switch compat
     */
    public void onSwitchRepeat(View switchCompat) {

        boolean on = ((SwitchCompat) switchCompat).isChecked();
        if (on) {
            this.mRepeat = "true";
            this.mRepeatText.setText("Every " + this.mRepeatNo + " " + this.mRepeatType + "(s)");
        } else {
            this.mRepeat = "false";
            this.mRepeatText.setText(R.string.repeat_off);
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
                (dialog, whichButton) -> {
                    if (input.getText().toString().length() == 0) {
                        mRepeatNo = Integer.toString(1);
                    } else {
                        mRepeatNo = input.getText().toString().trim();
                    }
                    mRepeatNoText.setText(mRepeatNo);
                    mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }


    /**
     * Update reminder.
     */
    public void updateReminder() {

        this.mReceivedReminder.setTitle(mTitle);
        this.mReceivedReminder.setDate(mDate);
        this.mReceivedReminder.setTime(mTime);
        this.mReceivedReminder.setRepeat(mRepeat);
        this.mReceivedReminder.setRepeatNo(mRepeatNo);
        this.mReceivedReminder.setRepeatType(mRepeatType);
        this.mReceivedReminder.setActive(mActive);

        rb.updateReminder(mReceivedReminder);

        this.mCalendar.set(Calendar.MONTH, --mMonth);
        this.mCalendar.set(Calendar.YEAR, this.mYear);
        this.mCalendar.set(Calendar.DAY_OF_MONTH, this.mDay);
        this.mCalendar.set(Calendar.HOUR_OF_DAY, this.mHour);
        this.mCalendar.set(Calendar.MINUTE, this.mMinute);
        this.mCalendar.set(Calendar.SECOND, 0);

        this.mAlarmReceiver.cancelAlarm(getApplicationContext(), this.mReceivedID);

        switch (mRepeatType) {
            case "Minute":
                this.mRepeatTime = Integer.parseInt(this.mRepeatNo) * milMinute;
                break;
            case "Hour":
                this.mRepeatTime = Integer.parseInt(this.mRepeatNo) * milHour;
                break;
            case "Day":
                this.mRepeatTime = Integer.parseInt(this.mRepeatNo) * milDay;
                break;
            case "Week":
                this.mRepeatTime = Integer.parseInt(this.mRepeatNo) * milWeek;
                break;
            case "Month":
                this.mRepeatTime = Integer.parseInt(this.mRepeatNo) * milMonth;
                break;
        }

        if (this.mActive.equals("true")) {
            if (this.mRepeat.equals("true")) {
                this.mAlarmReceiver.setRepeatAlarm(getApplicationContext(), this.mCalendar, this.mReceivedID, this.mRepeatTime);
            } else if (this.mRepeat.equals("false")) {
                this.mAlarmReceiver.setAlarm(getApplicationContext(), this.mCalendar, this.mReceivedID);
            }
        }

        Toast.makeText(getApplicationContext(), "Edited",
                Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    /**
     * On back pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save_reminder:
                this.mTitleText.setText(this.mTitle);
                if (this.mTitleText.getText().toString().length() == 0) {
                    this.mTitleText.setError("Reminder Title cannot be blank!");
                } else {
                    updateReminder();
                }
                return true;
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Changes Discarded",
                        Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}