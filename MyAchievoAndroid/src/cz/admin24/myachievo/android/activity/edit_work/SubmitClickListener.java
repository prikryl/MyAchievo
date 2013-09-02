package cz.admin24.myachievo.android.activity.edit_work;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.task.RegisterWorkTask;
import cz.admin24.myachievo.android.activity.main.Main2Activity;
import cz.admin24.myachievo.android.activity.main.Main2Intent;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class SubmitClickListener implements OnClickListener {

    private final Spinner              projectSpinner;
    private final Spinner              phaseSpinner;
    private final Spinner              activitySpinner;
    private final AutoCompleteTextView remarkAutoTV;
    private final DayPicker            dayPicker;
    private final Spinner              hoursSpinner;
    private final Spinner              minutesSpinner;
    private BaseActivity               context;


    public SubmitClickListener(Spinner projectSpinner, Spinner phaseSpinner, Spinner activitySpinner, AutoCompleteTextView remarkAutoTV, DayPicker dayPicker, Spinner hoursSpinner,
            Spinner minutesSpinner, BaseActivity context) {
        this.projectSpinner = projectSpinner;
        this.phaseSpinner = phaseSpinner;
        this.activitySpinner = activitySpinner;
        this.remarkAutoTV = remarkAutoTV;
        this.dayPicker = dayPicker;
        this.hoursSpinner = hoursSpinner;
        this.minutesSpinner = minutesSpinner;
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        String remark = remarkAutoTV.getText().toString();
        if (StringUtils.isBlank(remark)) {
            Toast.makeText(context, context.getText(R.string.edit_work_remark_blank_error), Toast.LENGTH_SHORT).show();
            return;
        }

        String projectId = ((Project) projectSpinner.getSelectedItem()).getId();
        String phaseId = ((ProjectPhase) phaseSpinner.getSelectedItem()).getId();
        String activityId = ((PhaseActivity) activitySpinner.getSelectedItem()).getId();
        Date day = dayPicker.getDay();
        final Integer hours = (Integer) hoursSpinner.getSelectedItem();
        final Integer minutes = (Integer) minutesSpinner.getSelectedItem();

        if ((hours + minutes) <= 0) {
            Toast.makeText(context, context.getText(R.string.edit_work_remark_registered_no_time_error), Toast.LENGTH_LONG).show();
            return;

        }

        new RegisterWorkTask(projectId, phaseId, activityId, day, hours, minutes, remark, context) {

            @Override
            protected void onPostExecute(BaseActivity context, List<WorkReport> result) {
                if (result.isEmpty()) {
                    Toast.makeText(context, context.getText(R.string.edit_work_remark_registered_no_work_error), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(context, context.getResources().getString(R.string.edit_work_registred, result.get(result.size() - 1).getRemark()), Toast.LENGTH_SHORT).show();
                context.setResult(Main2Activity.RESULT_REGISTRED_WORK, new Main2Intent(context));
                context.finish();
            }

        }.execute();

    }
}
