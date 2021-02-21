package com.ipbeja.easymed.Objects;

/**
 * The type Reminder.
 */
public class Reminder {
    /**
     * The M id.
     */
    private int mID;
    /**
     * The M title.
     */
    private String mTitle;
    /**
     * The M date.
     */
    private String mDate;
    /**
     * The M time.
     */
    private String mTime;
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
     * Instantiates a new Reminder.
     *
     * @param ID         the id
     * @param Title      the title
     * @param Date       the date
     * @param Time       the time
     * @param Repeat     the repeat
     * @param RepeatNo   the repeat no
     * @param RepeatType the repeat type
     * @param Active     the active
     */
    public Reminder(int ID, String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active) {
        mID = ID;
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mRepeatNo = RepeatNo;
        mRepeatType = RepeatType;
        mActive = Active;
    }

    /**
     * Instantiates a new Reminder.
     *
     * @param Title      the title
     * @param Date       the date
     * @param Time       the time
     * @param Repeat     the repeat
     * @param RepeatNo   the repeat no
     * @param RepeatType the repeat type
     * @param Active     the active
     */
    public Reminder(String Title, String Date, String Time, String Repeat, String RepeatNo, String RepeatType, String Active) {
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mRepeatNo = RepeatNo;
        mRepeatType = RepeatType;
        mActive = Active;
    }

    /**
     * Instantiates a new Reminder.
     */
    public Reminder() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return mID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(int ID) {
        mID = ID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        mDate = date;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        mTime = time;
    }

    /**
     * Gets repeat type.
     *
     * @return the repeat type
     */
    public String getRepeatType() {
        return mRepeatType;
    }

    /**
     * Sets repeat type.
     *
     * @param repeatType the repeat type
     */
    public void setRepeatType(String repeatType) {
        mRepeatType = repeatType;
    }

    /**
     * Gets repeat no.
     *
     * @return the repeat no
     */
    public String getRepeatNo() {
        return mRepeatNo;
    }

    /**
     * Sets repeat no.
     *
     * @param repeatNo the repeat no
     */
    public void setRepeatNo(String repeatNo) {
        mRepeatNo = repeatNo;
    }

    /**
     * Gets repeat.
     *
     * @return the repeat
     */
    public String getRepeat() {
        return mRepeat;
    }

    /**
     * Sets repeat.
     *
     * @param repeat the repeat
     */
    public void setRepeat(String repeat) {
        mRepeat = repeat;
    }

    /**
     * Gets active.
     *
     * @return the active
     */
    public String getActive() {
        return mActive;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(String active) {
        mActive = active;
    }
}
