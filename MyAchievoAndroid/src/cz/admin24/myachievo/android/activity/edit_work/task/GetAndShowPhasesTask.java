package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.List;

import android.widget.Spinner;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.BaseObjectSpinnerAdapter;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetOrderedPhaseNames;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetProjectPhases;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplaceProjetcPhases;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class GetAndShowPhasesTask extends SlowlyAhievoTask<Void, Void, BaseObjectSpinnerAdapter> {

    private final Spinner phaseSpinner;
    private final Project project;


    public GetAndShowPhasesTask(Project project, Spinner phaseSpinner, BaseActivity context) {
        super(context);
        this.project = project;
        this.phaseSpinner = phaseSpinner;
    }


    @Override
    protected BaseObjectSpinnerAdapter doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        String projectId = project.getId();
        List<ProjectPhase> phases = dbHelper.get(new CmdGetProjectPhases(projectId));
        if (phases.isEmpty()) {
            // there is no known project phases ... try to reload projects from web
            phases = achievoConnector.getPhases(projectId);
            dbHelper.update(new CmdReplaceProjetcPhases(project, phases));
        }
        List<String> phaseNames = dbHelper.get(new CmdGetOrderedPhaseNames(project));
        return new BaseObjectSpinnerAdapter(context, (List) phases, phaseNames);
    }


    @Override
    protected void onPostExecute(BaseActivity context, BaseObjectSpinnerAdapter adapter) {
        phaseSpinner.setAdapter(adapter);
    }
}