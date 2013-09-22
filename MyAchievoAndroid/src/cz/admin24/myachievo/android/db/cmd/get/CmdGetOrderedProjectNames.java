package cz.admin24.myachievo.android.db.cmd.get;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;

public class CmdGetOrderedProjectNames extends CmdGetOrderedNames {
    public CmdGetOrderedProjectNames() {
        super(WorkReportTable.COLUMN_NAME_PROJECT);
    }


    protected Cursor createCursor(SQLiteDatabase db) {
        return db.query(WorkReportTable.TABLE_NAME, new String[] { columnName }
                , null, null, null, null, WorkReportTable.COLUMN_NAME_DATE);
    }

}
