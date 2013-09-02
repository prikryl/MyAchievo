package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.List;

import android.widget.Spinner;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.BaseObjectSpinnerAdapter;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetOrderedProjectNames;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetProjects;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplaceProjetcs;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.Project;

public class GetAndShowProjectsTask extends SlowlyAhievoTask<Void, Void, BaseObjectSpinnerAdapter> {

    private final Spinner projectSpinner;


    public GetAndShowProjectsTask(Spinner projectSpinner, BaseActivity context) {
        super(context);
        this.projectSpinner = projectSpinner;
    }


    @Override
    protected BaseObjectSpinnerAdapter doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        List<Project> projects = dbHelper.get(new CmdGetProjects());
        if (projects.isEmpty()) {
            // there is no known projects ... try to reload projects from web
            projects = achievoConnector.getProjects();
            dbHelper.update(new CmdReplaceProjetcs(projects));
        }
        List<String> projectNames = dbHelper.get(new CmdGetOrderedProjectNames());
        return new BaseObjectSpinnerAdapter(context, (List) projects, projectNames);
    }


    @Override
    protected void onPostExecute(BaseActivity context, BaseObjectSpinnerAdapter adapter) {
        projectSpinner.setAdapter(adapter);
    }

}
