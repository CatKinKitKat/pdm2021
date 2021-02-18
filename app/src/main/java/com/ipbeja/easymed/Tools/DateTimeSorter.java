package com.ipbeja.easymed.Tools;


/**
 * The type Date time sorter.
 */
public class DateTimeSorter {

    /**
     * The M index.
     */
    public int mIndex;
    /**
     * The M date time.
     */
    public String mDateTime;

    /**
     * Instantiates a new Date time sorter.
     *
     * @param index    the index
     * @param DateTime the date time
     */
    public DateTimeSorter(int index, String DateTime) {
        this.mIndex = index;
        this.mDateTime = DateTime;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return this.mIndex;
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    public String getDateTime() {
        return this.mDateTime;
    }

}
