package cz.admin24.myachievo.web2.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.calendar.WorkReportEvent;

public class TimesheetUtils {

    public static RemainingTime countRemainingTime(List<WorkReport> reportedHours) {
        return countRemainingTimep(reportedHours, TimesheetConstants.CONTRACT_MINUTES);
    }


    public static RemainingTime countRemainingTime(Pair<Integer, Integer> remainingTime) {
        int left = TimesheetConstants.CONTRACT_MINUTES + remainingTime.getKey() * 60 + remainingTime.getValue();
        return getLeft(left);
    }


    public static RemainingTime countRemainingTime(List<WorkReportEvent> events, Integer expectedMinutes, Date startDate, Date endDate) {
        List<WorkReport> reportedHours = Lists.newArrayListWithCapacity(events.size());
        for (WorkReportEvent e : events) {
            if (e.getStart().before(startDate) || e.getStart().after(endDate)) {
                // skip event out of range...
                continue;
            }
            reportedHours.add(e.getWorkReport());
        }

        return countRemainingTimep(reportedHours, expectedMinutes);
    }


    private static RemainingTime countRemainingTimep(List<WorkReport> reportedHours, Integer expectedMinutes) {
        int sum = 0;
        for (WorkReport r : reportedHours) {
            sum += 60 * r.getHours() + r.getMinutes();
        }

        Integer left = expectedMinutes - sum;
        return getLeft(left);
    }


    private static RemainingTime getLeft(int left) {
        Integer leftHours = left / 60;
        Integer leftMinutes = left % 60;
        return new RemainingTime(leftHours, leftMinutes);
    }

}
