package cz.admin24.myachievo.android.db.cmd.truncate;

import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectPhaseTable;
import cz.admin24.myachievo.connector.http.dto.Project;

public class CmdTruncateProjectPhases extends CmdTruncate {

    private final Project project;


    public CmdTruncateProjectPhases(Project project) {
        super(ProjectPhaseTable.TABLE_NAME);
        this.project = project;
    }


    @Override
    public void execute(SQLiteDatabase db) {
        db.delete(tableName, ProjectPhaseTable.COLUMN_NAME_PROJECT_ID + "= ?", new String[] { project.getId() });
    }
}
