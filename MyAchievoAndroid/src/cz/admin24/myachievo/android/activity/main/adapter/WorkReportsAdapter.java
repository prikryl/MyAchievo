package cz.admin24.myachievo.android.activity.main.adapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DateUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cz.admin24.myachievo.android.R;
import cz.admin24.myachievo.android.tools.DateTools;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class WorkReportsAdapter extends ArrayAdapter<Entry<Date, List<WorkReport>>> {
    private static final Integer                CONTRACTED_MINUTES            = 8 * 60;
    public static final RefreshEntryPlaceholder REFRESH_ENTRY_PLACEHOLDER_TOP = new RefreshEntryPlaceholder();


    public static WorkReportsAdapter getInstance(Context context, List<WorkReport> reports) {
        return new WorkReportsAdapter(context, transformData(reports));
    }

    private final View  refreshView;
    private ProgressBar progressBar;
    private TextView    refreshMsg;


    protected WorkReportsAdapter(Context context, List<Entry<Date, List<WorkReport>>> data) {
        super(context, R.layout.workreport_item, data);
        refreshView = createRefreshView();
    }


    public void showProgressBar(boolean visible) {
        // if (progressBar == null || refreshMsg == null) {
        // return;
        // }
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
            refreshMsg.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            refreshMsg.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Entry<Date, List<WorkReport>> entry = getItem(position);
        if (entry == REFRESH_ENTRY_PLACEHOLDER_TOP) {
            return refreshView;
        }

        // don't reuse refresh layout, otherwise class cast exception...
        if (v == null || v == refreshView) {
            LayoutInflater inflanter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflanter.inflate(R.layout.workreport_item, null);
        }

        Date date = entry.getKey();
        List<WorkReport> reports = entry.getValue();

        TextView dayTV = (TextView) v.findViewById(R.id.workReport_item_dayTextView);
        dayTV.setText(getDayText(date));

        Integer reportedMinutes = 0;
        List<String> stringReports = new ArrayList<String>();
        for (WorkReport r : reports) {
            reportedMinutes += 60 * r.getHours() + r.getMinutes();
            stringReports.add(MessageFormat.format("{0}:{1}\t{2}", r.getHours(), r.getMinutes(), r.getRemark()));
        }
        ListView recordsListView = (ListView) v.findViewById(R.id.workReport_item_dayRecordsListView);
        recordsListView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stringReports));

        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.workReport_item_day_progressBar);
        progressBar.setMax(CONTRACTED_MINUTES);
        progressBar.setProgress(reportedMinutes);
        return v;
    }


    private View createRefreshView() {
        LayoutInflater inflanter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View refreshView = inflanter.inflate(R.layout.workreport_item_refresh_top, null);
        progressBar = (ProgressBar) refreshView.findViewById(R.id.workReport_itemRefreshTop_progress);
        refreshMsg = (TextView) refreshView.findViewById(R.id.workReport_itemRefreshTop_textView);
        return refreshView;
    }


    private String getDayText(Date day) {
        return DateTools.getDayTextShort(getContext(), day);
    }


    private static List<Entry<Date, List<WorkReport>>> transformData(List<WorkReport> reports) {
        SortedMap<Date, List<WorkReport>> data = new TreeMap<Date, List<WorkReport>>(new Comparator<Date>() {

            @Override
            public int compare(Date lhs, Date rhs) {
                return rhs.compareTo(lhs);
            }
        });

        for (WorkReport report : reports) {
            Date key = DateUtils.truncate(report.getDate(), Calendar.DAY_OF_MONTH);
            List<WorkReport> dayReports = data.get(key);
            if (dayReports == null) {
                dayReports = new ArrayList<WorkReport>();
                data.put(key, dayReports);
            }
            dayReports.add(report);
        }

        // put missing days...
        if (!data.isEmpty()) {
            Date firstDate = data.lastKey();
            Date lastExpectedKey = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH); // today
                                                                                          // 0:0
                                                                                          // AM
            Date date = firstDate;
            while (date.getTime() <= lastExpectedKey.getTime()) {
                if (!data.containsKey(date)) {
                    data.put(date, new ArrayList<WorkReport>());
                }
                date = DateUtils.addDays(date, 1);
            }
        }

        LinkedList<Entry<Date, List<WorkReport>>> ret = new LinkedList<Entry<Date, List<WorkReport>>>(data.entrySet());
        ret.addFirst(REFRESH_ENTRY_PLACEHOLDER_TOP);
        return ret;
    }

    /**
     * just entry, which is used as placeholder for refresh position
     *
     * @author pprikryl
     *
     */
    private static class RefreshEntryPlaceholder implements Entry<Date, List<WorkReport>> {

        @Override
        public Date getKey() {
            return null;
        }


        @Override
        public List<WorkReport> getValue() {
            return null;
        }


        @Override
        public List<WorkReport> setValue(List<WorkReport> object) {
            return null;
        }

    }

}
