package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import android.util.Pair;
import android.widget.Spinner;
import cz.admin24.myachievo.android.Constants;
import cz.admin24.myachievo.android.activity.base.AchievoConnectorTask;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetWorkReportsBetween;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class UpdateHoursMinutesByDayTask extends AchievoConnectorTask<Void, Void, Pair<Integer, Integer>> {

    private final Spinner hoursSpinner;
    private final Spinner minutesSpinner;
    private final Date    day;


    public UpdateHoursMinutesByDayTask(Spinner hoursSpinner, Spinner minutesSpinner, Date day, BaseActivity context) {
        super(context);
        this.hoursSpinner = hoursSpinner;
        this.minutesSpinner = minutesSpinner;
        this.day = day;
    }


    @Override
    protected Pair<Integer, Integer> doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        // work is only durng business days!
        // Calendar c = Calendar.getInstance();
        // c.setTime(day);
        // int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        // if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
        // return new Pair(0, 0);
        // }

        Date from = DateUtils.truncate(day, Calendar.DAY_OF_MONTH);
        Date to = DateUtils.addMilliseconds(DateUtils.addDays(from, 1), -1);
        List<WorkReport> reportedTime = dbHelper.get(new CmdGetWorkReportsBetween(from, to));

        Integer reportedMinutes = 0;
        for (WorkReport r : reportedTime) {
            reportedMinutes += r.getHours() * 60;
            reportedMinutes += r.getMinutes();
        }

        Integer sumLeftMinutes = Constants.CONTRACT_MINUTES - reportedMinutes;

        Integer leftHours = sumLeftMinutes / 60;
        Integer leftMinutes = sumLeftMinutes % 60;

        return new Pair<Integer, Integer>(leftHours, leftMinutes);
    }


    @Override
    protected void onPostExecute(BaseActivity context, Pair<Integer, Integer> result) {
        if (result == null) {
            return;
        }
        if (result.first < 0 || result.second < 0) {
            hoursSpinner.setSelection(0);
            minutesSpinner.setSelection(0);
        }
        hoursSpinner.setSelection(result.first);
        minutesSpinner.setSelection(result.second % 15);
    }

}
