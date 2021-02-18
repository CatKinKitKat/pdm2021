package com.ipbeja.easymed.Objects;

/**
 * The type Reminder item.
 */
public class ReminderItem {
    /**
     * The M title.
     */
    public String mTitle;
    /**
     * The M date time.
     */
    public String mDateTime;
    /**
     * The M repeat.
     */
    public String mRepeat;
    /**
     * The M repeat no.
     */
    public String mRepeatNo;
    /**
     * The M repeat type.
     */
    public String mRepeatType;
    /**
     * The M active.
     */
    public String mActive;

    /**
     * Instantiates a new Reminder item.
     *
     * @param Title      the title
     * @param DateTime   the date time
     * @param Repeat     the repeat
     * @param RepeatNo   the repeat no
     * @param RepeatType the repeat type
     * @param Active     the active
     */
    public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String Active) {
        this.mTitle = Title;
        this.mDateTime = DateTime;
        this.mRepeat = Repeat;
        this.mRepeatNo = RepeatNo;
        this.mRepeatType = RepeatType;
        this.mActive = Active;
    }
}
