package cz.admin24.myachievo.android.activity.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import cz.admin24.myachievo.android.ConstantsSP;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.activity.base.task.CleanCachesTask;
import cz.admin24.myachievo.android.activity.login.LoginIntent;
import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;

public abstract class BaseActivity extends Activity {
    private final AchievoConnector achievoConnector = new AchievoConnectorImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_base);
        configureConnector();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_base_logout) {
            logout();
            return true;
        }
        if (item.getItemId() == R.id.menu_base_clean_caches) {
            cleanCaches();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logout() {
        SharedPreferences preferences = getSharedPreferences(ConstantsSP.SP_CREDENTIALS, MODE_PRIVATE);
        preferences.edit().clear().apply();

        startActivity(new LoginIntent(this));
    }


    private void cleanCaches() {
        new CleanCachesTask(this).execute();
    }


    private void configureConnector() {
        SharedPreferences preferences = getSharedPreferences(ConstantsSP.SP_CREDENTIALS, MODE_PRIVATE);

        String username = preferences.getString(ConstantsSP.USERNAME, null);
        String password = preferences.getString(ConstantsSP.PASSWORD, null);

        achievoConnector.setCredentials(username, password);
    }


    public AchievoConnector getAchievoConnector() {
        return achievoConnector;
    }

}
