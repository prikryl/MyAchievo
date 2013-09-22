package cz.admin24.myachievo.android.db.cmd.get;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;
import cz.admin24.myachievo.connector.http.dto.Project;

public class CmdGetOrderedPhaseNames extends CmdGetOrderedNames {

    private final Project project;


    public CmdGetOrderedPhaseNames(Project project) {
        super(WorkReportTable.COLUMN_NAME_PHASE);
        this.project = project;
    }


    @Override
    protected Cursor createCursor(SQLiteDatabase db) {
        String projectString = project.getName();
        return db.query(WorkReportTable.TABLE_NAME, new String[] { columnName }
                , WorkReportTable.COLUMN_NAME_PROJECT + " = ?", new String[] { projectString }, null, null, WorkReportTable.COLUMN_NAME_DATE);
    }

}
