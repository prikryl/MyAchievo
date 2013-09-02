package cz.admin24.myachievo.android.activity.edit_work.adapter;

import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.BaseSpinnerAdapter;

public class MinutesAdapter extends BaseSpinnerAdapter<Integer> {
    private static final Integer[] OPTIONS = { 0, 15, 30, 45 };


    public MinutesAdapter(BaseActivity context) {
        super(context, OPTIONS);
    }

}