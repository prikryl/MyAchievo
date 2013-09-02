package cz.admin24.myachievo.android.db.cmd.replace;

import java.util.Collection;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectTable;
import cz.admin24.myachievo.android.db.cmd.truncate.CmdTruncateProjects;
import cz.admin24.myachievo.connector.http.dto.Project;

public class CmdReplaceProjetcs extends CmdReplace {
    private static final String       SQL_INSERT = String.format("INSERT INTO %s ( %s , %s , %s , %s , %s , %s ) VALUES (?, ?, ?, ?, ?, ?)",
                                                         ProjectTable.TABLE_NAME, ProjectTable.COLUMN_NAME_ID, ProjectTable.COLUMN_NAME_CODE, ProjectTable.COLUMN_NAME_NAME,
                                                         ProjectTable.COLUMN_NAME_COORDINATOR, ProjectTable.COLUMN_NAME_BEGIN, ProjectTable.COLUMN_NAME_END);
    private final Collection<Project> projects;


    public CmdReplaceProjetcs(Collection<Project> projects) {
        this.projects = projects;
    }


    public void execute(SQLiteDatabase db) {
        new CmdTruncateProjects().execute(db);
        SQLiteStatement statement = db.compileStatement(SQL_INSERT);

        try {
            for (Project r : projects) {
                statement.clearBindings();
                bind(statement, 1, r.getId());
                bind(statement, 2, r.getCode());
                bind(statement, 3, r.getName());
                bind(statement, 4, r.getCoordinator());
                bind(statement, 5, r.getBegin());
                bind(statement, 6, r.getEnd());

                statement.execute();
            }
        } finally {
            statement.close();
        }

    }

}
