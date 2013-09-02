package cz.admin24.myachievo.android.activity.edit_work.listener;

import java.util.Date;

import android.widget.Spinner;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.DayPicker.OnDayChangedListener;
import cz.admin24.myachievo.android.activity.edit_work.task.UpdateHoursMinutesByDayTask;

public class DayChangedListener implements OnDayChangedListener {

    private final Spinner      hoursSpinner;
    private final Spinner      minutesSpinner;
    private final BaseActivity context;


    public DayChangedListener(Spinner hoursSpinner, Spinner minutesSpinner, BaseActivity context) {
        this.hoursSpinner = hoursSpinner;
        this.minutesSpinner = minutesSpinner;
        this.context = context;
    }


    @Override
    public void onDayChanged(Date day) {
        new UpdateHoursMinutesByDayTask(hoursSpinner, minutesSpinner,day, context).execute();
    }

}
