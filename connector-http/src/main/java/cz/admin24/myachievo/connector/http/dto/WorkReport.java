package cz.admin24.myachievo.connector.http.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WorkReport implements Serializable {
    private final String id;
    private Date         date;
    private String       project;
    private String       phase;
    private String       activity;
    private String       remark;
    private Integer      hours;
    private Integer      minutes;


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


    public void setDate(Date date) {
        this.date = date;
    }


    public void setProject(String project) {
        this.project = project;
    }


    public void setPhase(String phase) {
        this.phase = phase;
    }


    public void setActivity(String activity) {
        this.activity = activity;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public void setHours(Integer hours) {
        this.hours = hours;
    }


    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, false, false);
    }


    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WorkReport)) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(id, ((WorkReport) obj).getId());
    }
}
