package cz.admin24.myachievo.android.activity.edit_work.task;

import android.view.View;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.base.AchievoConnectorTask;
import cz.admin24.myachievo.android.activity.base.BaseActivity;

public abstract class SlowlyAhievoTask<Params, Progress, Result> extends AchievoConnectorTask<Params, Progress, Result> {

    private View progressBar;


    public SlowlyAhievoTask(BaseActivity context) {
        super(context);
        progressBar = context.findViewById(R.id.edit_work_progressLayout);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
    }


    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        progressBar.setVisibility(View.GONE);
    }

}