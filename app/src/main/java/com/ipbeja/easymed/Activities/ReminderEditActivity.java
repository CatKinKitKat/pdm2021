package com.ipbeja.easymed.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.Alarm.DataBase.ReminderDatabase;
import com.ipbeja.easymed.Alarm.Receivers.AlarmReceiver;
import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.R;

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
     * The Title text.
     */
    private EditText titleText;
    /**
     * The Date text.
     */
    private TextView dateText, /**
     * The Time text.
     */
    timeText, /**
     * The Repeat text.
     */
    repeatText, /**
     * The Repeat no text.
     */
    repeatNoText, /**
     * The Repeat type text.
     */
    repeatTypeText;
    /**
     * The Mute btn.
     */
    private FloatingActionButton muteBtn;
    /**
     * The Notificate btn.
     */
    private FloatingActionButton notificateBtn;
    /**
     * The Title.
     */
    private String title;
    /**
     * The Time.
     */
    private String time;
    /**
     * The Date.
     */
    private String date;
    /**
     * The Repeat no.
     */
    private String repeatNo;
    /**
     * The Repeat type.
     */
    private String repeatType;
    /**
     * The Active.
     */
    private String active;
    /**
     * The Repeat.
     */
    private String repeat;
    /**
     * The Received id.
     */
    private int receivedID;
    /**
     * The Year.
     */
    private int year, /**
     * The Month.
     */
    month, /**
     * The Hour.
     */
    hour, /**
     * The Minute.
     */
    minute, /**
     * The Day.
     */
    day;
    /**
     * The Repeat time.
     */
    private long repeatTime;
    /**
     * The Calendar.
     */
    private Calendar calendar;
    /**
     * The Received reminder.
     */
    private Reminder receivedReminder;
    /**
     * The Reminder database.
     */
    private ReminderDatabase reminderDatabase;
    /**
     * The Alarm receiver.
     */
    private AlarmReceiver alarmReceiver;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);


        this.titleText = findViewById(R.id.reminder_title);
        this.dateText = findViewById(R.id.set_date);
        this.timeText = findViewById(R.id.set_time);
        this.repeatText = findViewById(R.id.set_repeat);
        this.repeatNoText = findViewById(R.id.set_repeat_no);
        this.repeatTypeText = findViewById(R.id.set_repeat_type);
        this.muteBtn = findViewById(R.id.starred1);
        this.notificateBtn = findViewById(R.id.starred2);
        SwitchCompat mRepeatSwitch = findViewById(R.id.repeat_switch);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.edit_reminder_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title = s.toString().trim();
                titleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        this.receivedID = Integer.parseInt(getIntent().getStringExtra(EXTRA_REMINDER_ID));


        this.reminderDatabase = new ReminderDatabase(this);
        this.receivedReminder = reminderDatabase.getReminder(receivedID);


        this.title = receivedReminder.getTitle();
        this.date = receivedReminder.getDate();
        this.time = receivedReminder.getTime();
        this.repeat = receivedReminder.getRepeat();
        this.repeatNo = receivedReminder.getRepeatNo();
        this.repeatType = receivedReminder.getRepeatType();
        this.active = receivedReminder.getActive();


        this.titleText.setText(title);
        this.dateText.setText(date);
        this.timeText.setText(time);
        this.repeatNoText.setText(repeatNo);
        this.repeatTypeText.setText(repeatType);
        this.repeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");


        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            this.titleText.setText(savedTitle);
            this.title = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            this.timeText.setText(savedTime);
            this.time = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            this.dateText.setText(savedDate);
            this.date = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            this.repeatText.setText(saveRepeat);
            this.repeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            this.repeatNoText.setText(savedRepeatNo);
            this.repeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            this.repeatTypeText.setText(savedRepeatType);
            this.repeatType = savedRepeatType;

            this.active = savedInstanceState.getString(KEY_ACTIVE);
        }


        if (active.equals("false")) {
            this.muteBtn.setVisibility(View.VISIBLE);
            this.notificateBtn.setVisibility(View.GONE);

        } else if (active.equals("true")) {
            this.muteBtn.setVisibility(View.GONE);
            this.notificateBtn.setVisibility(View.VISIBLE);
        }


        if (repeat.equals("false")) {
            mRepeatSwitch.setChecked(false);
            this.repeatText.setText(R.string.repeat_off);

        } else if (repeat.equals("true")) {
            mRepeatSwitch.setChecked(true);
        }


        this.calendar = Calendar.getInstance();
        this.alarmReceiver = new AlarmReceiver();

        String[] mDateSplit = date.split("/");
        String[] mTimeSplit = time.split(":");

        this.day = Integer.parseInt(mDateSplit[0]);
        this.month = Integer.parseInt(mDateSplit[1]);
        this.year = Integer.parseInt(mDateSplit[2]);
        this.hour = Integer.parseInt(mTimeSplit[0]);
        this.minute = Integer.parseInt(mTimeSplit[1]);
    }


    /**
     * On save instance state.
     *
     * @param outState the out state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, titleText.getText());
        outState.putCharSequence(KEY_TIME, timeText.getText());
        outState.putCharSequence(KEY_DATE, dateText.getText());
        outState.putCharSequence(KEY_REPEAT, repeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, repeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, repeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE, active);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Sets time.
     *
     * @param v the v
     */
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();
        this.hour = now.get(Calendar.HOUR_OF_DAY);
        this.minute = now.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(
                this, (view, hourOfDay, minute) -> {
            this.hour = hourOfDay;
            this.minute = minute;
            if (minute < 10) {
                this.time = hourOfDay + ":" + "0" + minute;
            } else {
                this.time = hourOfDay + ":" + minute;
            }
            this.timeText.setText(this.time);
        }, this.hour, this.minute, false);
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
            this.month++;
            this.day = dayOfMonth;
            this.month = month;
            this.year = year;
            this.date = dayOfMonth + "/" + month + "/" + year;
            this.dateText.setText(this.date);
        }, this.year, this.month, this.day);
        dpd.show();
    }


    /**
     * Select fab 1.
     *
     * @param v the v
     */
    public void selectFab1(View v) {
        this.muteBtn = findViewById(R.id.starred1);
        this.muteBtn.setVisibility(View.GONE);
        this.notificateBtn = findViewById(R.id.starred2);
        this.notificateBtn.setVisibility(View.VISIBLE);
        this.active = "true";
    }


    /**
     * Select fab 2.
     *
     * @param v the v
     */
    public void selectFab2(View v) {
        this.notificateBtn = findViewById(R.id.starred2);
        this.notificateBtn.setVisibility(View.GONE);
        this.muteBtn = findViewById(R.id.starred1);
        this.muteBtn.setVisibility(View.VISIBLE);
        this.active = "false";
    }


    /**
     * On switch repeat.
     *
     * @param view the view
     */
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            this.repeat = "true";
            this.repeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");

        } else {
            this.repeat = "false";
            this.repeatText.setText(R.string.repeat_off);
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

            repeatType = items[item];
            repeatTypeText.setText(repeatType);
            repeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");
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
                        repeatNo = Integer.toString(1);
                    } else {
                        repeatNo = input.getText().toString().trim();
                    }
                    repeatNoText.setText(repeatNo);
                    repeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");
                });
        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {

        });
        alert.show();
    }


    /**
     * Update reminder.
     */
    public void updateReminder() {

        this.receivedReminder.setTitle(title);
        this.receivedReminder.setDate(date);
        this.receivedReminder.setTime(time);
        this.receivedReminder.setRepeat(repeat);
        this.receivedReminder.setRepeatNo(repeatNo);
        this.receivedReminder.setRepeatType(repeatType);
        this.receivedReminder.setActive(active);


        reminderDatabase.updateReminder(receivedReminder);


        this.calendar.set(Calendar.MONTH, --month);
        this.calendar.set(Calendar.YEAR, year);
        this.calendar.set(Calendar.DAY_OF_MONTH, day);
        this.calendar.set(Calendar.HOUR_OF_DAY, hour);
        this.calendar.set(Calendar.MINUTE, minute);
        this.calendar.set(Calendar.SECOND, 0);


        this.alarmReceiver.cancelAlarm(getApplicationContext(), receivedID);


        switch (repeatType) {
            case "Minute":
                this.repeatTime = Integer.parseInt(repeatNo) * milMinute;
                break;
            case "Hour":
                this.repeatTime = Integer.parseInt(repeatNo) * milHour;
                break;
            case "Day":
                this.repeatTime = Integer.parseInt(repeatNo) * milDay;
                break;
            case "Week":
                this.repeatTime = Integer.parseInt(repeatNo) * milWeek;
                break;
            case "Month":
                this.repeatTime = Integer.parseInt(repeatNo) * milMonth;
                break;
        }


        if (active.equals("true")) {
            if (repeat.equals("true")) {
                this.alarmReceiver.setRepeatAlarm(getApplicationContext(), calendar, receivedID, repeatTime);
            } else if (repeat.equals("false")) {
                this.alarmReceiver.setAlarm(getApplicationContext(), calendar, receivedID);
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
                this.titleText.setText(title);

                if (titleText.getText().toString().length() == 0)
                    this.titleText.setError("Reminder Title cannot be blank!");

                else {
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