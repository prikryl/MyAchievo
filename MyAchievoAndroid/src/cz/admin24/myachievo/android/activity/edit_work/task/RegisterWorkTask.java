package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public abstract class RegisterWorkTask extends SlowlyAhievoTask<Void, Void, Void> {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterWorkTask.class);
    //
    private final String        projectId;
    private final String        phaseId;
    private final String        activityId;
    private final Date          day;
    private final Integer       hours;
    private final Integer       minutes;
    private final String        remark;


    public RegisterWorkTask(String projectId, String phaseId, String activityId, Date day, Integer hours, Integer minutes, String remark, BaseActivity context) {
        super(context);
        this.projectId = projectId;
        this.phaseId = phaseId;
        this.activityId = activityId;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.remark = remark;
    }


    @Override
    protected Void doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        achievoConnector.registerHours(day, hours, minutes, projectId, phaseId, activityId, remark);
        // LOG.info("Registered work in day '{}': {}", day, registerHours);
        return null;
    }

}
