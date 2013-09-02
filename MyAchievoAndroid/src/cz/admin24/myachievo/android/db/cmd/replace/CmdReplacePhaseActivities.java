package cz.admin24.myachievo.android.db.cmd.replace;

import java.util.Collection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import cz.admin24.myachievo.android.db.MyAchievoContract.PhaseActivityTable;
import cz.admin24.myachievo.android.db.cmd.truncate.CmdTruncatePhaseActivities;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdReplacePhaseActivities extends CmdReplace {
    private static final String             SQL_INSERT = String.format("INSERT INTO %s ( %s ,%s , %s ) VALUES (?, ?, ?)",
                                                               PhaseActivityTable.TABLE_NAME, PhaseActivityTable.COLUMN_NAME_ID,
                                                               PhaseActivityTable.COLUMN_NAME_PHASE_ID, PhaseActivityTable.COLUMN_NAME_NAME);
    private final Collection<PhaseActivity> activities;
    private final ProjectPhase              phase;


    public CmdReplacePhaseActivities(ProjectPhase phase, Collection<PhaseActivity> activities) {
        this.phase = phase;
        this.activities = activities;
    }


    public void execute(SQLiteDatabase db) {
        new CmdTruncatePhaseActivities(phase).execute(db);
        SQLiteStatement statement = db.compileStatement(SQL_INSERT);
        try {
            for (PhaseActivity a : activities) {
                statement.clearBindings();
                bind(statement, 1, a.getId());
                bind(statement, 2, a.getPhaseId());
                bind(statement, 3, a.getName());

                statement.execute();
            }
        } finally {
            statement.close();
        }
    }

}
