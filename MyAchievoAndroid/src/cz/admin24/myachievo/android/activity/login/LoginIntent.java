package cz.admin24.myachievo.android.activity.login;

import cz.admin24.myachievo.android.activity.base.BaseActivity;
import android.content.Intent;

public class LoginIntent extends Intent {

    /**
     * start login and set as top of activities
     *
     * @param activity
     */
    public LoginIntent(BaseActivity activity) {
        super(activity, LoginActivity.class);
        setFlags(FLAG_ACTIVITY_CLEAR_TOP);
    }

}
