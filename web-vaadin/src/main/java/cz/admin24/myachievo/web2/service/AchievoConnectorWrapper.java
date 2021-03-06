package cz.admin24.myachievo.web2.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

/**
 * this wrapper is only used to convert authentication exceptions to springs exceptions and removes
 * unused methods
 *
 * @author pprikryl
 *
 */
public class AchievoConnectorWrapper {
    private static final Logger   LOG                  = LoggerFactory.getLogger(AchievoConnectorWrapper.class);

    private static final int      DEFAULT_HISTORY_DAYS = 40;

    public final AchievoConnector connector;
    public final WorkReportCache  workReportCache;


    protected AchievoConnectorWrapper() {
        // only for AOP proxy Use!
        this.connector = null;
        this.workReportCache = null;
    }


    public AchievoConnectorWrapper(AchievoConnector connector, WorkReportCache workReportCache) {
        this.connector = connector;
        this.workReportCache = workReportCache;
        if (!workReportCache.isInitialized()) {
            Calendar c = Calendar.getInstance();
            Date to = c.getTime();
            c.add(Calendar.DAY_OF_YEAR, -DEFAULT_HISTORY_DAYS);
            Date from = c.getTime();
            // initialize work report cache
            workReportCache.initialize(getHours(from, to));
        }
    }


    public String getFullUserName() {
        return connector.getFullUserName();
    }


    public List<Project> getProjects() throws IllegalStateException, AccessDeniedException {
        try {
            return connector.getProjects();
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }


    public List<ProjectPhase> getPhases(String projectId) throws IllegalStateException, AccessDeniedException {
        try {
            return connector.getPhases(projectId);
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }


    public List<PhaseActivity> getActivities(String projectId, String phaseId) throws IllegalStateException, AccessDeniedException {
        try {
            return connector.getActivities(projectId, phaseId);
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }


    public void registerHours(Date day, Integer hours, Integer minutes, String projectId, String phaseId, String activityId, String remark) throws IllegalStateException,
            AccessDeniedException {
        try {
            connector.registerHours(day, hours, minutes, projectId, phaseId, activityId, remark);
            return;
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
    }


    public void updateRegiteredHours(String workReportId, Date day, Integer hours, Integer minutes, String projectId, String phaseId, String activityId, String remark)
            throws IllegalStateException,
            AccessDeniedException {
        try {
            connector.updateRegiteredHours(workReportId, day, hours, minutes, projectId, phaseId, activityId, remark);
            return;
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
    }


    public List<WorkReport> getHours(Date from, Date to) throws IllegalStateException, AccessDeniedException {
        try {

            List<WorkReport> hours = connector.getHours(from, to);
            workReportCache.put(hours);
            return hours;
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return Collections.EMPTY_LIST;
    }


    public void deleteRegisteredHour(String workReportId) throws IllegalStateException, AccessDeniedException {
        try {
            connector.deleteRegisteredHour(workReportId);
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
    }


    private void handleException(AuthentizationException e) throws AccessDeniedException {
        LOG.error("User {} has no permission to call this method with used credentials", SecurityContextHolder.getContext().getAuthentication(), e);
        throw new AccessDeniedException("User has no permission to call this method with used credentials", e);
    }


    private void handleException(IOException e) throws IllegalStateException {
        LOG.error("Failed to call achievo, IO exception occurred.", e);
        throw new IllegalStateException("Failed to call achievo, IO exception occurred.", e);

    }

}
