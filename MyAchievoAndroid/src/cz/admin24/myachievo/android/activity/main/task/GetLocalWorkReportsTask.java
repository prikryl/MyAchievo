package cz.admin24.myachievo.android.activity.main.task;

import java.io.IOException;
import java.util.List;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cz.admin24.myachievo.android.activity.base.AchievoConnectorTask;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.main.adapter.WorkReportsAdapter;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetWorkReports;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class GetLocalWorkReportsTask extends AchievoConnectorTask<Void, Void, WorkReportsAdapter> {
    private final CmdGetWorkReports  cmdGetWorkReports = new CmdGetWorkReports();
    private final ListView           reportView;
    private final WorkReportsAdapter adapter;


    public GetLocalWorkReportsTask(BaseActivity context, ListView reportView) {
        super(context);
        this.reportView = reportView;
        adapter = (WorkReportsAdapter) reportView.getAdapter();
    }


    /*
     * (non-Javadoc)
     *
     * @see cz.admin24.myachievo.android.activity.base.AchievoConnectorTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        adapter.showProgressBar(true);
    }


    @Override
    protected WorkReportsAdapter doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        List<WorkReport> reports = dbHelper.get(cmdGetWorkReports);
        WorkReportsAdapter adapter = WorkReportsAdapter.getInstance(context, reports);
        return adapter;
    }


    @Override
    protected void onPostExecute(BaseActivity context, WorkReportsAdapter adapter) {
        this.adapter.showProgressBar(false);
        if (adapter == null) {
            return;
        }

        reportView.setAdapter(adapter);
    }
}
