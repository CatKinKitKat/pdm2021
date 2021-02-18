package com.ipbeja.easymed.Objects;


/**
 * The type Reminder.
 */
public class Reminder {
    /**
     * The        this.m id.
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
        this.mID = ID;
        this.mTitle = Title;
        this.mDate = Date;
        this.mTime = Time;
        this.mRepeat = Repeat;
        this.mRepeatNo = RepeatNo;
        this.mRepeatType = RepeatType;
        this.mActive = Active;
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
        this.mTitle = Title;
        this.mDate = Date;
        this.mTime = Time;
        this.mRepeat = Repeat;
        this.mRepeatNo = RepeatNo;
        this.mRepeatType = RepeatType;
        this.mActive = Active;
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
        return this.mID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(int ID) {
        this.mID = ID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.mTitle;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return this.mDate;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.mDate = date;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return this.mTime;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.mTime = time;
    }

    /**
     * Gets repeat type.
     *
     * @return the repeat type
     */
    public String getRepeatType() {
        return this.mRepeatType;
    }

    /**
     * Sets repeat type.
     *
     * @param repeatType the repeat type
     */
    public void setRepeatType(String repeatType) {
        this.mRepeatType = repeatType;
    }

    /**
     * Gets repeat no.
     *
     * @return the repeat no
     */
    public String getRepeatNo() {
        return this.mRepeatNo;
    }

    /**
     * Sets repeat no.
     *
     * @param repeatNo the repeat no
     */
    public void setRepeatNo(String repeatNo) {
        this.mRepeatNo = repeatNo;
    }

    /**
     * Gets repeat.
     *
     * @return the repeat
     */
    public String getRepeat() {
        return this.mRepeat;
    }

    /**
     * Sets repeat.
     *
     * @param repeat the repeat
     */
    public void setRepeat(String repeat) {
        this.mRepeat = repeat;
    }

    /**
     * Gets active.
     *
     * @return the active
     */
    public String getActive() {
        return this.mActive;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(String active) {
        this.mActive = active;
    }
}
