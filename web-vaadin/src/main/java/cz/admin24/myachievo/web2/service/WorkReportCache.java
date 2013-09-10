package cz.admin24.myachievo.web2.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import cz.admin24.myachievo.connector.http.dto.WorkReport;

@Repository
@Scope("session")
public class WorkReportCache implements Serializable {

    private static final Comparator<WorkReport> WORK_REPORT_ORDER_COMPARATOR = new Comparator<WorkReport>() {

                                                                                 /**
                                                                                  * just do reverse
                                                                                  * order by day
                                                                                  *
                                                                                  * @param o1
                                                                                  * @param o2
                                                                                  * @return
                                                                                  */
                                                                                 @Override
                                                                                 public int compare(WorkReport o1, WorkReport o2) {
                                                                                     if (o1 == null || o2 == null) {
                                                                                         return 0;
                                                                                     }
                                                                                     Date d1 = o1.getDate();
                                                                                     Date d2 = o2.getDate();
                                                                                     if (d1 == null || d2 == null) {
                                                                                         return 0;
                                                                                     }
                                                                                     return -(d1.compareTo(d2));
                                                                                 }
                                                                             };
    //
    private boolean                             initialized                  = false;
    private List<WorkReport>                    hours                        = new ArrayList<WorkReport>();


    public boolean isInitialized() {
        return initialized;
    }


    public void initialize(List<WorkReport> hours) {
        put(hours);
        initialized = true;
    }


    public void put(List<WorkReport> newHours) {
        Set<WorkReport> newHoursSet = new HashSet<WorkReport>(hours);
        newHoursSet.addAll(newHours);

        ArrayList<WorkReport> newHoursMerged = new ArrayList<WorkReport>(newHoursSet);

        Collections.sort(newHoursMerged, WORK_REPORT_ORDER_COMPARATOR);

        hours = newHoursMerged;
    }


    public List<String> getProjectNamesOrder() {
        Set<String> ret = new LinkedHashSet<String>();
        for (WorkReport report : hours) {
            ret.add(report.getProject());
        }
        return new ArrayList<String>(ret);
    }


    public List<String> getPhaseNamesOrder(String projectName) {
        Set<String> ret = new LinkedHashSet<String>();
        for (WorkReport report : hours) {
            if (StringUtils.equals(projectName, report.getProject())) {
                ret.add(report.getPhase());
            }
        }
        return new ArrayList<String>(ret);
    }


    public List<String> getActivityNamesOrder(String projectName, String phaseName) {
        Set<String> ret = new LinkedHashSet<String>();
        for (WorkReport report : hours) {
            if (StringUtils.equals(projectName, report.getProject()) && StringUtils.equals(phaseName, report.getPhase())) {
                ret.add(report.getActivity());
            }
        }
        return new ArrayList<String>(ret);
    }


    public List<String> getRemarks() {
        Set<String> ret = new LinkedHashSet<String>();
        for (WorkReport report : hours) {
            ret.add(report.getRemark());
        }
        return new ArrayList<String>(ret);
    }
}
