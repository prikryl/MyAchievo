package cz.admin24.myachievo.connector.http.dto;

public class PhaseActivity extends BaseObject {

    private final String phaseId;


    public String getPhaseId() {
        return phaseId;
    }


    public PhaseActivity(String id, String phaseId, String name) {
        super(id, name);
        this.phaseId = phaseId;
    }

}
