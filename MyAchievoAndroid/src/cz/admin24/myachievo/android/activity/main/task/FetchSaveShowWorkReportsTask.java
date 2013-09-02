package cz.admin24.myachievo.android.activity.main.task;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import android.widget.ListView;
import cz.admin24.myachievo.android.activity.main.Main2Activity;
import cz.admin24.myachievo.android.activity.main.adapter.WorkReportsAdapter;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplaceWorkReports;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class FetchSaveShowWorkReportsTask extends GetLocalWorkReportsTask {
    private static final Integer FETCH_DAYS = 40;


    public FetchSaveShowWorkReportsTask(Main2Activity context, ListView reportView) {
        super(context, reportView);
    }


    @Override
    protected WorkReportsAdapter doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        // save data to DB

        Date to = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH); // tomorrow
                                                                                               // 0:0
                                                                                               // AM
        Date from = DateUtils.addDays(to, -FETCH_DAYS - 1);

        List<WorkReport> reports = achievoConnector.getHours(from, to);
        dbHelper.update(new CmdReplaceWorkReports(reports));

        // load data from DB
        return super.doInBackground(achievoConnector, dbHelper, params);
    }
}
