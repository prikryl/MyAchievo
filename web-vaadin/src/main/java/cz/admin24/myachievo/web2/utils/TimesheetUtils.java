package cz.admin24.myachievo.web2.utils;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class TimesheetUtils {

    public static Pair<Integer, Integer> countRemainingTime(List<WorkReport> reportedHours) {
        int sum = 0;
        for (WorkReport r : reportedHours) {
            sum += 60 * r.getHours() + r.getMinutes();
        }

        Integer left = TimesheetConstants.CONTRACT_MINUTES - sum;
        return getLeft(left);
    }


    public static Pair<Integer, Integer> countRemainingTime(Pair<Integer, Integer> remainingTime) {
        int left = TimesheetConstants.CONTRACT_MINUTES + remainingTime.getKey() * 60 + remainingTime.getValue();
        return getLeft(left);
    }


    private static Pair<Integer, Integer> getLeft(int left) {
        Integer leftHours = left / 60;
        Integer leftMinutes = left % 60;
        return ImmutablePair.of(leftHours, leftMinutes);
    }

}
