package cz.admin24.myachievo.android.db.cmd.replace;

import java.util.Collection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectPhaseTable;
import cz.admin24.myachievo.android.db.cmd.truncate.CmdTruncateProjectPhases;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdReplaceProjetcPhases extends CmdReplace {
    private static final String            SQL_INSERT = String.format("INSERT INTO %s ( %s , %s , %s ) VALUES (?, ?, ?)",
                                                              ProjectPhaseTable.TABLE_NAME, ProjectPhaseTable.COLUMN_NAME_ID, ProjectPhaseTable.COLUMN_NAME_PROJECT_ID,
                                                              ProjectPhaseTable.COLUMN_NAME_NAME);
    private final Collection<ProjectPhase> phases;
    private final Project                  project;


    public CmdReplaceProjetcPhases(Project project, Collection<ProjectPhase> phases) {
        this.project = project;
        this.phases = phases;
    }


    public void execute(SQLiteDatabase db) {
        new CmdTruncateProjectPhases(project).execute(db);
        SQLiteStatement statement = db.compileStatement(SQL_INSERT);
        try {
            for (ProjectPhase r : phases) {
                statement.clearBindings();
                bind(statement, 1, r.getId());
                bind(statement, 2, r.getProjectId());
                bind(statement, 3, r.getName());

                statement.execute();
            }
        } finally {
            statement.close();
        }
    }

}
