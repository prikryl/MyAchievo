package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectPhaseTable;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdGetProjectPhases extends CmdGet<ProjectPhase> {

    private final String projectId;


    public CmdGetProjectPhases(String projectId) {
        this.projectId = projectId;
    }


    @Override
    public List<ProjectPhase> execute(SQLiteDatabase db) {
        List<ProjectPhase> ret = new ArrayList<ProjectPhase>();
        Cursor cursor = db.query(ProjectPhaseTable.TABLE_NAME, null, ProjectPhaseTable.COLUMN_NAME_PROJECT_ID + " = ?", new String[] { projectId }, null, null, null);
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String id = getString(cursor, ProjectPhaseTable.COLUMN_NAME_ID);
                String projectId = getString(cursor, ProjectPhaseTable.COLUMN_NAME_PROJECT_ID);
                String name = getString(cursor, ProjectPhaseTable.COLUMN_NAME_NAME);

                ProjectPhase p = new ProjectPhase(id, projectId, name);
                ret.add(p);

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return ret;
    }

}
