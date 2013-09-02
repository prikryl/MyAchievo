package cz.admin24.myachievo.android.activity.edit_work;

import java.util.Date;

import android.os.Bundle;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.adapter.HoursAdapter;
import cz.admin24.myachievo.android.activity.edit_work.adapter.MinutesAdapter;
import cz.admin24.myachievo.android.activity.edit_work.listener.DayChangedListener;
import cz.admin24.myachievo.android.activity.edit_work.listener.PhaseSelectedListener;
import cz.admin24.myachievo.android.activity.edit_work.listener.ProjectSelectedListener;
import cz.admin24.myachievo.android.activity.edit_work.task.GetAndSetLastUsedRemarks;
import cz.admin24.myachievo.android.activity.edit_work.task.GetAndShowProjectsTask;

public class EditWorkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        Spinner projectSpinner = (Spinner) findViewById(R.id.edit_work_projectSpinner);
        Spinner phaseSpinner = (Spinner) findViewById(R.id.edit_work_phaseSpinner);
        Spinner activitySpinner = (Spinner) findViewById(R.id.edit_work_activitySpinner);
        AutoCompleteTextView remarkAutoTV = (AutoCompleteTextView) findViewById(R.id.edit_work_remarkAutoTV);
        DayPicker dayPicker = (DayPicker) findViewById(R.id.edit_work_dateDayPicker);
        Spinner hoursSpinner = (Spinner) findViewById(R.id.edit_work_hoursSpinner);
        Spinner minutesSpinner = (Spinner) findViewById(R.id.edit_work_minutesSpinner);
        Button submitBtn = (Button) findViewById(R.id.edit_work_submit);

        hoursSpinner.setAdapter(new HoursAdapter(this));
        minutesSpinner.setAdapter(new MinutesAdapter(this));

        new GetAndShowProjectsTask(projectSpinner, this).execute();
        new GetAndSetLastUsedRemarks(remarkAutoTV, this).execute();

        projectSpinner.setOnItemSelectedListener(new ProjectSelectedListener(phaseSpinner, this));
        phaseSpinner.setOnItemSelectedListener(new PhaseSelectedListener(activitySpinner, this));
        dayPicker.setOnDayChangedListener(new DayChangedListener(hoursSpinner, minutesSpinner, this));
        submitBtn.setOnClickListener(new SubmitClickListener(projectSpinner, phaseSpinner, activitySpinner, remarkAutoTV, dayPicker, hoursSpinner, minutesSpinner, this));

        dayPicker.setDay(new Date());
        remarkAutoTV.setThreshold(1);

    }

}
