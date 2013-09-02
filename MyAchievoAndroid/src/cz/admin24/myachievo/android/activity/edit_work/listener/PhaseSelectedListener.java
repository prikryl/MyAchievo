package cz.admin24.myachievo.android.activity.edit_work.listener;

import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.task.GetAndShowActivitiesTask;
import cz.admin24.myachievo.android.activity.edit_work.task.GetAndShowPhasesTask;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class PhaseSelectedListener implements OnItemSelectedListener {

    private final BaseActivity context;
    private final Spinner      activitySpinner;


    public PhaseSelectedListener(Spinner activitySpinner, BaseActivity context) {
        this.activitySpinner = activitySpinner;
        this.context = context;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ProjectPhase phase = (ProjectPhase) parent.getItemAtPosition(position);
        new GetAndShowActivitiesTask(phase, activitySpinner, context).execute();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // NOP
    }

}
