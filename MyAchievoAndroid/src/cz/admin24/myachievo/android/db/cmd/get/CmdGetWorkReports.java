package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class CmdGetWorkReports extends CmdGet<WorkReport> {
    // private static final String[] COLUMNS = new String[] {
    // WorkReportTable.COLUMN_NAME_PROJECT, WorkReportTable.COLUMN_NAME_PHASE,
    // WorkReportTable.COLUMN_NAME_ACTIVITY, WorkReportTable.COLUMN_NAME_REMARK,
    // WorkReportTable.COLUMN_NAME_DATE, WorkReportTable.COLUMN_NAME_HOURS,
    // WorkReportTable.COLUMN_NAME_MINUTES
    // };

    @Override
    public List<WorkReport> execute(SQLiteDatabase db) {
        List<WorkReport> ret = new ArrayList<WorkReport>();
        Cursor cursor = db.query(WorkReportTable.TABLE_NAME, null, getSelection(), getSelectionArgs(), null, null, null);
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String id = getString(cursor, WorkReportTable.COLUMN_NAME_ID);
                String project = getString(cursor, WorkReportTable.COLUMN_NAME_PROJECT);
                String phase = getString(cursor, WorkReportTable.COLUMN_NAME_PHASE);
                String activity = getString(cursor, WorkReportTable.COLUMN_NAME_ACTIVITY);
                String remark = getString(cursor, WorkReportTable.COLUMN_NAME_REMARK);
                Date date = getDate(cursor, WorkReportTable.COLUMN_NAME_DATE);
                Integer hours = getInteger(cursor, WorkReportTable.COLUMN_NAME_HOURS);
                Integer minutes = getInteger(cursor, WorkReportTable.COLUMN_NAME_MINUTES);

                WorkReport r = new WorkReport(id, date, project, phase, activity, remark, hours, minutes);
                ret.add(r);

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return ret;
    }


    protected String[] getSelectionArgs() {
        return null;
    }


    protected String getSelection() {
        return null;
    }

}
