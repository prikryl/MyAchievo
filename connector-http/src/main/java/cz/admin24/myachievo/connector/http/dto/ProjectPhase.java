package cz.admin24.myachievo.connector.http.dto;

public class ProjectPhase extends BaseObject {

    private final String projectId;


    public ProjectPhase(String id, String projectId, String name) {
        super(id, name);
        this.projectId = projectId;
    }


    public String getProjectId() {
        return projectId;
    }

}
