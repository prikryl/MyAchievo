package cz.admin24.myachievo.web2.utils;

import org.apache.commons.lang3.tuple.MutablePair;

public class RemainingTime extends MutablePair<Integer, Integer> {

    public RemainingTime(Integer leftHours, Integer leftMinutes) {
        super(leftHours, leftMinutes);
    }


    public boolean isPositive() {
        return getMinutes() + getHours() > 0;
    }


    public Integer getHours() {
        return getLeft();
    }


    public Integer getMinutes() {
        return getRight();
    }
}
