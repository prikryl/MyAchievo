package cz.admin24.myachievo.connector.http;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public interface AchievoConnector {

    void setCredentials(String username, String password);


    void authentize(String username, String password) throws AuthentizationException, IOException;


    String getFullUserName();


    List<Project> getProjects() throws AuthentizationException, IOException;


    List<ProjectPhase> getPhases(String projectId) throws AuthentizationException, IOException;


    List<PhaseActivity> getActivities(String projectId, String phaseId) throws AuthentizationException, IOException;


    List<WorkReport> registerHours(Date day, Integer hours, Integer minutes, String projectId, String phaseId, String activityId, String remark) throws AuthentizationException,
            IOException;


    List<WorkReport> updateRegiteredHours(String workReportId, Date day, Integer hours, Integer minutes, String projectId, String phaseId, String activityId, String remark)
            throws AuthentizationException,
            IOException;


    void deleteRegisteredHour(String workReportId) throws AuthentizationException, IOException;


    List<WorkReport> getHours(Date from, Date to) throws AuthentizationException, IOException;

}