package cz.admin24.myachievo.android.activity.edit_work.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.task.GetAndShowPhasesTask;
import cz.admin24.myachievo.connector.http.dto.Project;

public class ProjectSelectedListener implements OnItemSelectedListener {

    private final Spinner      phaseSpinner;
    private final BaseActivity context;


    public ProjectSelectedListener(Spinner phaseSpinner, BaseActivity context) {
        this.phaseSpinner = phaseSpinner;
        this.context = context;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Project project = (Project) parent.getItemAtPosition(position);
        new GetAndShowPhasesTask(project, phaseSpinner, context).execute();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // NOP
    }

}
