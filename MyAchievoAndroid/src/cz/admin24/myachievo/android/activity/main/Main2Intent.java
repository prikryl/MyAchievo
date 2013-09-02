package cz.admin24.myachievo.android.activity.main;

import android.content.Context;
import android.content.Intent;

public class Main2Intent extends Intent {

    /**
     * start main2 activity and set as top of activities
     *
     * @param context
     */
    public Main2Intent(Context context) {
        super(context, Main2Activity.class);
        setFlags(FLAG_ACTIVITY_CLEAR_TOP);
    }

}
