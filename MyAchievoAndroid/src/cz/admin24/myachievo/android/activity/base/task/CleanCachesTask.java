package cz.admin24.myachievo.android.activity.base.task;

import java.io.IOException;

import android.content.Intent;
import cz.admin24.myachievo.android.activity.base.AchievoConnectorTask;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.truncate.TruncateAll;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;

public class CleanCachesTask extends AchievoConnectorTask<Void, Void, Void> {
    private final TruncateAll cmdTruncateAll = new TruncateAll();


    public CleanCachesTask(BaseActivity context) {
        super(context);
    }


    /*
     * (non-Javadoc)
     *
     * @see cz.admin24.myachievo.android.activity.base.AchievoConnectorTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        cmdTruncateAll.execute(dbHelper.getWritableDatabase());
        return null;
    }


    @Override
    protected void onPostExecute(BaseActivity context, Void result) {
        // restart activity
        Intent intent = context.getIntent();
        context.finish();
        context.startActivity(intent);
    }

}
