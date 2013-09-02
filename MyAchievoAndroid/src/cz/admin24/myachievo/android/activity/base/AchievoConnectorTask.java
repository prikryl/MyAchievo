package cz.admin24.myachievo.android.activity.base;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.login.LoginIntent;
import cz.admin24.myachievo.android.db.DatabaseException;
import cz.admin24.myachievo.android.db.MyAchievoDbHelper;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AuthentizationException;

public abstract class AchievoConnectorTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static final Logger     LOG       = LoggerFactory.getLogger(AchievoConnectorTask.class);
    protected final BaseActivity    context;
    private final MyAchievoDbHelper dbHelper;
    //
    private Params[]                params    = null;
    private Exception               exception = null;


    public AchievoConnectorTask(BaseActivity context) {
        super();
        this.context = context;
        this.dbHelper = new MyAchievoDbHelper(context);
    }


    /**
     * This wrapper add support for achievo connector
     *
     * @param achievoConnector
     * @param dbHelper2
     * @param params
     * @return
     * @throws AuthentizationException
     * @throws IOException
     */
    protected abstract Result doInBackground(AchievoConnector achievoConnector, MyAchievoDbHelper dbHelper, Params... params) throws AuthentizationException, IOException;


    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context.setProgressBarIndeterminateVisibility(true);
    }


    @Override
    protected Result doInBackground(Params... params) {
        this.params = params;
        try {
            return doInBackground(context.getAchievoConnector(), dbHelper, params);
        } catch (IOException e) {
            exception = e;
        } catch (DatabaseException e) {
            exception = e;
        } catch (AuthentizationException e) {
            exception = e;
        }
        return null;
    }


    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (exception instanceof IOException) {
            processException((IOException) exception, params);
        }
        if (exception instanceof DatabaseException) {
            processException((DatabaseException) exception, params);
        }
        if (exception instanceof AuthentizationException) {
            processAuthenticationException((AuthentizationException) exception, params);
        }
        onPostExecute(context, result);
        context.setProgressBarIndeterminateVisibility(false);
    }


    protected abstract void onPostExecute(BaseActivity context, Result result);


    /**
     * auth exception should terminate current activity and start Login activity, which is root
     * activity.
     *
     * @param e
     * @param params
     */
    private void processAuthenticationException(AuthentizationException e, Params[] params) {
        LOG.info("User is unauthentized, switch to login activity!", e);
        Toast.makeText(context, R.string.base_activity_user_not_authentized, Toast.LENGTH_SHORT).show();
        Intent intent = new LoginIntent(context);
        context.startActivity(intent);
        context.finish();
    }


    private void processException(Exception e, Params[] params) {
        LOG.error("Failed to call achievo proxy with params {}", (Object) params, e);
        // new ExceptionDialogFragment<Params>(activity, params,
        // e).show(activity.getFragmentManager(), null);

    }

}
