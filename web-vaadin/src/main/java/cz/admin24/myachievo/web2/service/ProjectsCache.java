package cz.admin24.myachievo.web2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

@Repository
@Scope("session")
public class ProjectsCache {
    private List<Project>                                  projects         = new ArrayList<Project>();
    private Map<String, List<ProjectPhase>>                projectsPhases   = new ConcurrentHashMap<String, List<ProjectPhase>>();
    private Map<Pair<String, String>, List<PhaseActivity>> phasesActivities = new ConcurrentHashMap<Pair<String, String>, List<PhaseActivity>>();

    @Autowired
    private AchievoConnectorWrapper                        achievoConnector;


    public List<Project> getProjects() {
        if (projects.isEmpty()) {
            projects = achievoConnector.getProjects();
        }
        return projects;
    }


    public List<ProjectPhase> getPhases(String projectId) {
        List<ProjectPhase> phases = projectsPhases.get(projectId);
        if (CollectionUtils.isEmpty(phases)) {
            phases = achievoConnector.getPhases(projectId);
            projectsPhases.put(projectId, phases);
        }
        return phases;
    }


    public List<PhaseActivity> getActivities(String projectId, String phaseId) {
        Pair<String, String> key = ImmutablePair.of(projectId, phaseId);
        List<PhaseActivity> activities = phasesActivities.get(key);
        if (CollectionUtils.isEmpty(activities)) {
            activities = achievoConnector.getActivities(projectId, phaseId);
            phasesActivities.put(key, activities);
        }
        return activities;
    }

}
