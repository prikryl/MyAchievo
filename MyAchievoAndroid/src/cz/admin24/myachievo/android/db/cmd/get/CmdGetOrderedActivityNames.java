package cz.admin24.myachievo.android.db.cmd.get;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdGetOrderedActivityNames extends CmdGetOrderedNames {

    private final ProjectPhase phase;


    public CmdGetOrderedActivityNames(ProjectPhase phase) {
        super(WorkReportTable.COLUMN_NAME_ACTIVITY);
        this.phase = phase;
    }


    @Override
    protected Cursor createCursor(SQLiteDatabase db) {
        String phaseString = phase.getName();
        return db.query(WorkReportTable.TABLE_NAME, new String[] { columnName }
                , WorkReportTable.COLUMN_NAME_PHASE + " = ?", new String[] { phaseString }, null, null, WorkReportTable.COLUMN_NAME_DATE);
    }

}