package com.ibm.openpages.support.util;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {
    private static DateComparator _instance = null;

    public static DateComparator getInstance() {
        if (_instance == null) {
            _instance = new DateComparator();
        }

        return _instance;
    }

    @Override
    public int compare(Date o1, Date o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 != null) {
            return o1.compareTo(o2);
        } else {
            return o2.compareTo(o1) * -1;
        }
    }

    public static int compareDates(Date o1, Date o2) {
        return getInstance().compare(o1, o2);
    }
}
