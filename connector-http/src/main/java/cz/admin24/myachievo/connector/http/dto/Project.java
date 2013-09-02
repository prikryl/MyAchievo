package cz.admin24.myachievo.connector.http.dto;

import java.util.Date;

public class Project extends BaseObject {

    private final String code;
    private final String coordinator;
    private final Date   begin;
    private final Date   end;


    public Project(String id, String code, String name, String coordinator, Date begin, Date end) {
        super(id, name);
        this.code = code;
        this.coordinator = coordinator;
        this.begin = begin;
        this.end = end;
    }


    public String getCode() {
        return code;
    }


    public String getCoordinator() {
        return coordinator;
    }


    public Date getBegin() {
        return begin;
    }


    public Date getEnd() {
        return end;
    }

}
