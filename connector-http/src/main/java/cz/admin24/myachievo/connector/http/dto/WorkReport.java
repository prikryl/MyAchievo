package cz.admin24.myachievo.connector.http.dto;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WorkReport {
    private final String  id;
    private final Date    date;
    private final String  project;
    private final String  phase;
    private final String  activity;
    private final String  remark;
    private final Integer hours;
    private final Integer minutes;


    public WorkReport(String id, Date date, String project, String phase, String activity, String remark, Integer hours, Integer minutes) {
        this.id = id;
        this.date = date;
        this.project = project;
        this.phase = phase;
        this.activity = activity;
        this.remark = remark;
        this.hours = hours;
        this.minutes = minutes;
    }


    public String getId() {
        return id;
    }


    public Date getDate() {
        return date;
    }


    public String getProject() {
        return project;
    }


    public String getPhase() {
        return phase;
    }


    public String getActivity() {
        return activity;
    }


    public String getRemark() {
        return remark;
    }


    public Integer getHours() {
        return hours;
    }


    public Integer getMinutes() {
        return minutes;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, false, false);
    }
}
