package cz.admin24.myachievo.web2.calendar;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;

public class CalendarUrl {
    public static enum CalendarViewType {
        DAY, WEEK, MONTH
    }

    private static final Logger LOG = LoggerFactory.getLogger(CalendarUrl.class);
    //
    private CalendarViewType    type;
    private Date                date;


    public CalendarUrl(String url) {
        this();
        String[] params = StringUtils.split(url, "/");
        if (ArrayUtils.isEmpty(params)) {
            return;
        }

        int urlLen = params.length;

        if (urlLen >= 1) {
            type = CalendarViewType.valueOf(params[0]);
        }

        if (urlLen >= 2) {
            try {
                date = new Date(Long.valueOf(params[1]));
            } catch (NumberFormatException e) {
                LOG.warn("Failed to parse day from URL {}", params[1], e);
            }
        }

    }


    public CalendarUrl() {
        type = CalendarViewType.WEEK;
        date = new Date();
    }


    public CalendarUrl(Page currentPage) {
        this(StringUtils.substringAfter(currentPage.getUriFragment(), "/"));
    }


    public String toFragment() {
        return CalendarView.NAME + "/" + type + "/" + date.getTime();
    }


    public String toUrl() {
        return "#!" + toFragment();
    }


    public Date getDate() {
        return date;
    }


    public Date getStartDate() {
        switch (type) {
        case MONTH:
            return DateUtils.truncate(date, Calendar.MONTH);
        case WEEK:
            Calendar c = Calendar.getInstance(UI.getCurrent().getLocale());
            c.setTime(DateUtils.truncate(date, Calendar.DAY_OF_MONTH));
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

            return c.getTime();
            // return new DateMidnight(date).withDayOfWeek(Calendar.MONDAY).toDate();
            // return DateUtils.truncate(date, Calendar.WEEK_OF_MONTH);
        case DAY:
        default:
            return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        }
    }


    public Date getEndDate() {
        Calendar c = Calendar.getInstance(UI.getCurrent().getLocale());
        c.setTime(getStartDate());
        switch (type) {
        case MONTH:
            c.add(Calendar.MONTH, 1);
            break;
        case WEEK:
            c.add(Calendar.WEEK_OF_YEAR, 1);
            break;
        case DAY:
        default:
            c.add(Calendar.DAY_OF_YEAR, 1);
            break;
        }
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }


    public void prev() {
        date = next(-1);
    }


    public void next() {
        date = next(1);
    }


    public Date next(Integer val) {
        switch (type) {
        case MONTH:
            return DateUtils.addMonths(date, val);
        case WEEK:
            return DateUtils.addWeeks(date, val);
        case DAY:
        default:
            return DateUtils.addDays(date, val);
        }
    }


    public CalendarViewType getType() {
        return type;
    }


    public void setType(CalendarViewType type) {
        this.type = type;
    }


    public void setDate(Date date) {
        this.date = date;
    }

}
