package cz.admin24.myachievo.android.db.cmd.replace;

import java.util.Collection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;
import cz.admin24.myachievo.android.db.cmd.truncate.CmdTruncateWorkReports;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class CmdReplaceWorkReports extends CmdReplace {
    private static final String          SQL_INSERT = String.format("INSERT INTO %s ( %s , %s , %s , %s , %s , %s , %s  ) VALUES (?, ?, ?, ?, ?, ?, ?)",
                                                            WorkReportTable.TABLE_NAME, WorkReportTable.COLUMN_NAME_PROJECT, WorkReportTable.COLUMN_NAME_PHASE,
                                                            WorkReportTable.COLUMN_NAME_ACTIVITY,
                                                            WorkReportTable.COLUMN_NAME_REMARK, WorkReportTable.COLUMN_NAME_DATE, WorkReportTable.COLUMN_NAME_HOURS,
                                                            WorkReportTable.COLUMN_NAME_MINUTES);
    private final Collection<WorkReport> reports;


    public CmdReplaceWorkReports(Collection<WorkReport> reports) {
        this.reports = reports;
    }


    public void execute(SQLiteDatabase db) {
        new CmdTruncateWorkReports().execute(db);
        SQLiteStatement statement = db.compileStatement(SQL_INSERT);

        try {
            for (WorkReport r : reports) {
                statement.clearBindings();
                bind(statement, 1, r.getProject());
                bind(statement, 2, r.getPhase());
                bind(statement, 3, r.getActivity());
                bind(statement, 4, r.getRemark());
                bind(statement, 5, r.getDate());
                bind(statement, 6, r.getHours());
                bind(statement, 7, r.getMinutes());

                statement.execute();
            }
        } finally {
            statement.close();
        }

    }

}
