package cz.admin24.myachievo.web2.service;

import java.io.IOException;
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
    private static final Logger    LOG = LoggerFactory.getLogger(AchievoConnectorWrapper.class);

    private final AchievoConnector connector;


    protected AchievoConnectorWrapper() {
        // only for AOP proxy Use!
        this(null);
    }


    public AchievoConnectorWrapper(AchievoConnector connector) {
        this.connector = connector;
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


    public List<WorkReport> registerHours(Date day, Integer hours, Integer minutes, String projectId, String phaseId, String activityId, String remark) throws IllegalStateException,
            AccessDeniedException {
        try {
            return connector.registerHours(day, hours, minutes, projectId, phaseId, activityId, remark);
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }


    public List<WorkReport> getHours(Date from, Date to) throws IllegalStateException, AccessDeniedException {
        try {
            return connector.getHours(from, to);
        } catch (AuthentizationException e) {
            handleException(e);
        } catch (IOException e) {
            handleException(e);
        }
        return null;
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
