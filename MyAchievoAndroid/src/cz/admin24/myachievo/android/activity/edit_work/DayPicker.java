package cz.admin24.myachievo.android.activity.edit_work;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import cz.admin24.myachievo.android.tools.DateTools;

public class DayPicker extends EditText {

    private Date                 day;
    private OnDayChangedListener onDayChangedListener;


    public DayPicker(Context context) {
        super(context);
        init();
    }


    public DayPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public DayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setFocusable(false);
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateSpinner();
            }
        });
    }


    public void setDay(Date day) {
        this.day = day;
        setText(DateTools.getDayText(getContext(), day));
        if (onDayChangedListener != null) {
            onDayChangedListener.onDayChanged(day);
        }
    }


    public Date getDay() {
        return day;
    }


    public void setOnDayChangedListener(OnDayChangedListener l) {
        this.onDayChangedListener = l;
    }


    private void showDateSpinner() {
        Calendar c = Calendar.getInstance();
        c.setTime(getDay());

        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getContext(), new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setDay(c.getTime());
            }
        }, year, monthOfYear, dayOfMonth).show();
    }

    public static interface OnDayChangedListener {
        void onDayChanged(Date day);
    }
}
