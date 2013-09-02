package cz.admin24.myachievo.android.db.cmd.truncate;

import cz.admin24.myachievo.android.db.MyAchievoContract.ProjectTable;

public class CmdTruncateProjects extends CmdTruncate {

    public CmdTruncateProjects() {
        super(ProjectTable.TABLE_NAME);
    }
}
