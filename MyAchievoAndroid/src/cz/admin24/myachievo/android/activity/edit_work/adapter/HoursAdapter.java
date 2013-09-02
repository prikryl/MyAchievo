package cz.admin24.myachievo.android.activity.edit_work.adapter;

import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.android.activity.edit_work.BaseSpinnerAdapter;

public class HoursAdapter extends BaseSpinnerAdapter<Integer> {
    private static final Integer[] OPTIONS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };


    public HoursAdapter(BaseActivity context) {
        super(context, OPTIONS);
    }

}