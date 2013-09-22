package cz.admin24.myachievo.android.db.cmd.truncate;

import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.PhaseActivityTable;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectPhaseTable;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectTable;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;

public class TruncateAll extends CmdTruncate {

    public TruncateAll() {
        super(null);
    }


    @Override
    public void execute(SQLiteDatabase db) {
        db.delete(WorkReportTable.TABLE_NAME, null, null);
        db.delete(ProjectTable.TABLE_NAME, null, null);
        db.delete(ProjectPhaseTable.TABLE_NAME, null, null);
        db.delete(PhaseActivityTable.TABLE_NAME, null, null);
    }
}
