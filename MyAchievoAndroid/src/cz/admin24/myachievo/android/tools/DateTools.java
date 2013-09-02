package cz.admin24.myachievo.android.tools;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import android.content.Context;
import cz.admin24.myachievo.android.R;

public class DateTools {

    private static final FastDateFormat DATE_FORMAT_WEEK_SHORT = FastDateFormat.getInstance("EEE");
    private static final FastDateFormat DATE_FORMAT_WEEK       = FastDateFormat.getInstance("EEE");
    private static final FastDateFormat DATE_FORMAT_UNI_SHORT  = FastDateFormat.getDateInstance(FastDateFormat.SHORT);


    public static String getDayTextShort(Context context, Date day) {
        return getDayText(context, day, true);
    }


    public static String getDayText(Context context, Date day) {
        return getDayText(context, day, false);
    }


    public static String getDayText(Context context, Date day, Boolean shortFormat) {
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, -6);
        Date beforeWeek = c.getTime();

        if (DateUtils.isSameDay(now, day)) {
            return context.getString(R.string.workReport_item_day_today);
        }
        if (DateUtils.isSameDay(yesterday, day)) {
            return context.getString(R.string.workReport_item_day_yesterday);
        }
        if (beforeWeek.getTime() < day.getTime() && day.getTime() < now.getTime()) {
            if (shortFormat) {
                return DATE_FORMAT_WEEK_SHORT.format(day);
            } else {
                return DATE_FORMAT_WEEK.format(day);
            }
        }
        return DATE_FORMAT_UNI_SHORT.format(day);
    }

}
