package cz.admin24.myachievo.android.activity.edit_work.task;

import java.io.IOException;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import cz.admin24.myachievo.android.activity.base.AchievoConnectorTask;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGetLastRemarks;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;

public class GetAndSetLastUsedRemarks extends AchievoConnectorTask<Void, Void, ArrayAdapter<String>> {

    private final AutoCompleteTextView remarkAutoTV;


    public GetAndSetLastUsedRemarks(AutoCompleteTextView remarkAutoTV, BaseActivity context) {
        super(context);
        this.remarkAutoTV = remarkAutoTV;
    }


    @Override
    protected ArrayAdapter<String> doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Void... params) throws AuthentizationException, IOException {
        List<String> lastRemarks = dbHelper.get(new CmdGetLastRemarks());
        return new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, lastRemarks);
    }


    @Override
    protected void onPostExecute(BaseActivity context, ArrayAdapter<String> result) {
        remarkAutoTV.setAdapter(result);
    }

}
