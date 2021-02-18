package com.ipbeja.easymed.Tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * The type Date time comparator.
 */
public class DateTimeComparator implements Comparator {
    /**
     * The F.
     */
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");

    /**
     * Compare int.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    public int compare(Object a, Object b) {
        String o1 = ((DateTimeSorter) a).getDateTime();
        String o2 = ((DateTimeSorter) b).getDateTime();

        try {
            return this.dateFormat.parse(o1).compareTo(this.dateFormat.parse(o2));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}