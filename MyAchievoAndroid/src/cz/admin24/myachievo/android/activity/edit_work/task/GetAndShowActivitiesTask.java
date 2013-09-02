package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.List;

import android.widget.Spinner;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.BaseObjectSpinnerAdapter;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetOrderedActivityNames;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetPhaseActivities;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplacePhaseActivities;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class GetAndShowActivitiesTask extends SlowlyAhievoTask<Void, Void, BaseObjectSpinnerAdapter> {

    private final Spinner      activitySpinner;
    private final ProjectPhase phase;


    public GetAndShowActivitiesTask(ProjectPhase phase, Spinner activitySpinner, BaseActivity context) {
        super(context);
        this.phase = phase;
        this.activitySpinner = activitySpinner;
    }


    @Override
    protected BaseObjectSpinnerAdapter doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        String phaseId = phase.getId();
        List<PhaseActivity> phases = dbHelper.get(new CmdGetPhaseActivities(phaseId));
        if (phases.isEmpty()) {
            // there is no known project phases ... try to reload projects from web
            phases = achievoConnector.getActivities(phase.getProjectId(), phase.getId());
            dbHelper.update(new CmdReplacePhaseActivities(phase, phases));
        }
        List<String> phaseNames = dbHelper.get(new CmdGetOrderedActivityNames(phase));
        return new BaseObjectSpinnerAdapter(context, (List) phases, phaseNames);
    }


    @Override
    protected void onPostExecute(BaseActivity context, BaseObjectSpinnerAdapter adapter) {
        activitySpinner.setAdapter(adapter);
    }
}