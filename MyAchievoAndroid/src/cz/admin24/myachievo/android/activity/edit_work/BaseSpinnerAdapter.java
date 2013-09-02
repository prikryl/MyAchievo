package cz.admin24.myachievo.android.activity.edit_work;

import java.util.Arrays;
import java.util.List;

import android.widget.ArrayAdapter;
import cz.admin24.myachievo.android.activity.base.BaseActivity;

public class BaseSpinnerAdapter<T> extends ArrayAdapter<T> {

    public BaseSpinnerAdapter(BaseActivity context, List<T> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


    public BaseSpinnerAdapter(BaseActivity context, T[] objects) {
        this(context, Arrays.asList(objects));
    }

}