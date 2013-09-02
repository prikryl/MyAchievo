package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.PhaseActivityTable;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;

public class CmdGetPhaseActivities extends CmdGet<PhaseActivity> {

    private final String phaseId;


    public CmdGetPhaseActivities(String phaseId) {
        this.phaseId = phaseId;
    }


    @Override
    public List<PhaseActivity> execute(SQLiteDatabase db) {
        List<PhaseActivity> ret = new ArrayList<PhaseActivity>();
        Cursor cursor = db.query(PhaseActivityTable.TABLE_NAME, null, PhaseActivityTable.COLUMN_NAME_PHASE_ID + " = ?", new String[] { phaseId }, null, null, null);
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String id = getString(cursor, PhaseActivityTable.COLUMN_NAME_ID);
                String name = getString(cursor, PhaseActivityTable.COLUMN_NAME_NAME);

                PhaseActivity p = new PhaseActivity(id, phaseId, name);
                ret.add(p);

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return ret;
    }

}
