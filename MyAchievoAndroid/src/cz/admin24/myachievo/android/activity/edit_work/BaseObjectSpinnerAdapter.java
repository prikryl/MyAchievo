package cz.admin24.myachievo.android.activity.edit_work;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cz.admin24.myachievo.android.activity.base.BaseActivity;
import cz.admin24.myachievo.connector.http.dto.BaseObject;

public class BaseObjectSpinnerAdapter extends BaseSpinnerAdapter<BaseObject> {

    public BaseObjectSpinnerAdapter(BaseActivity context, List<BaseObject> objects, final List<String> order) {
        super(context, objects);
        sort(new Comparator<BaseObject>() {

            @Override
            public int compare(BaseObject lhs, BaseObject rhs) {
                int lIdx = order.indexOf(lhs.getName());
                int rIdx = order.indexOf(rhs.getName());

                lIdx = lIdx == -1 ? Integer.MAX_VALUE : lIdx;
                rIdx = rIdx == -1 ? Integer.MAX_VALUE : rIdx;

                return lIdx - rIdx;
            }
        });

        Collections.sort(objects, new Comparator<BaseObject>() {

            @Override
            public int compare(BaseObject lhs, BaseObject rhs) {
                int lIdx = order.indexOf(lhs.getName());
                int rIdx = order.indexOf(rhs.getName());

                lIdx = lIdx == -1 ? Integer.MAX_VALUE : lIdx;
                rIdx = rIdx == -1 ? Integer.MAX_VALUE : rIdx;

                return lIdx - rIdx;
            }
        });
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
        dropDownView.setText(getItem(position).getName());
        return dropDownView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(getItem(position).getName());
        return view;

    }

}
