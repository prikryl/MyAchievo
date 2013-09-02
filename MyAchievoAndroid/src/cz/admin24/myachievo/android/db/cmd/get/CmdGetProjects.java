package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectTable;
import cz.admin24.myachievo.connector.http.dto.Project;

public class CmdGetProjects extends CmdGet<Project> {

    @Override
    public List<Project> execute(SQLiteDatabase db) {
        List<Project> ret = new ArrayList<Project>();
        Cursor cursor = db.query(ProjectTable.TABLE_NAME, null, null, null, null, null, null);
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String id = getString(cursor, ProjectTable.COLUMN_NAME_ID);
                String name = getString(cursor, ProjectTable.COLUMN_NAME_NAME);
                String coordinator = getString(cursor, ProjectTable.COLUMN_NAME_COORDINATOR);
                String code = getString(cursor, ProjectTable.COLUMN_NAME_CODE);
                Date begin = getDate(cursor, ProjectTable.COLUMN_NAME_BEGIN);
                Date end = getDate(cursor, ProjectTable.COLUMN_NAME_END);

                Project p = new Project(id, code, name, coordinator, begin, end);
                ret.add(p);

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return ret;
    }

}
