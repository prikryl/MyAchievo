package cz.admin24.myachievo.android.db.cmd.truncate;

import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.PhaseActivityTable;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdTruncatePhaseActivities extends CmdTruncate {

    private final ProjectPhase phase;


    public CmdTruncatePhaseActivities(ProjectPhase phase) {
        super(PhaseActivityTable.TABLE_NAME);
        this.phase = phase;
    }


    @Override
    public void execute(SQLiteDatabase db) {
        db.delete(tableName, PhaseActivityTable.COLUMN_NAME_PHASE_ID + "= ?", new String[] { phase.getId() });
    }
}
