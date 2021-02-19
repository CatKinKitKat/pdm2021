package com.ipbeja.easymed.Activities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ipbeja.easymed.Activities.AlarmPort.Database.AlarmURIManager;
import com.ipbeja.easymed.R;

/**
 * The type Alarm cursor adapter.
 */
public class AlarmCursorAdapter extends CursorAdapter {

    /**
     * The M color generator.
     */
    private final ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    /**
     * The M title text.
     */
    private TextView mTitleText, /**
     * The M date and time text.
     */
    mDateAndTimeText, /**
     * The M repeat info text.
     */
    mRepeatInfoText;
    /**
     * The M active image.
     */
    private ImageView mActiveImage, /**
     * The M thumbnail image.
     */
    mThumbnailImage;
    /**
     * The M drawable builder.
     */
    private TextDrawable mDrawableBuilder;

    /**
     * Instantiates a new Alarm cursor adapter.
     *
     * @param context the context
     * @param c       the c
     */
    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * New view view.
     *
     * @param context the context
     * @param cursor  the cursor
     * @param parent  the parent
     * @return the view
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.reminder_item, parent, false);
    }

    /**
     * Bind view.
     *
     * @param view    the view
     * @param context the context
     * @param cursor  the cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText = view.findViewById(R.id.recycle_title);
        mDateAndTimeText = view.findViewById(R.id.recycle_date_time);
        mRepeatInfoText = view.findViewById(R.id.recycle_repeat_info);
        mActiveImage = view.findViewById(R.id.active_image);
        mThumbnailImage = view.findViewById(R.id.thumbnail_image);

        int titleColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_REPEAT_NO);
        int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_REPEAT_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(AlarmURIManager.Entry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;


        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setActiveImage(active);


    }

    /**
     * Sets reminder title.
     *
     * @param title the title
     */
// Set reminder title view
    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    /**
     * Sets reminder date time.
     *
     * @param datetime the datetime
     */
// Set date and time views
    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }

    /**
     * Sets reminder repeat info.
     *
     * @param repeat     the repeat
     * @param repeatNo   the repeat no
     * @param repeatType the repeat type
     */
// Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if (repeat.equals("true")) {
            mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
        } else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
    }

    /**
     * Set active image.
     *
     * @param active the active
     */
// Set active image as on or off
    public void setActiveImage(String active) {
        if (active.equals("true")) {
            mActiveImage.setImageResource(R.drawable.notificate);
        } else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.mute);
        }
    }
}
