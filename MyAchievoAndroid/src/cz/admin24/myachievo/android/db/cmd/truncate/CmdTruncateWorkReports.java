package cz.admin24.myachievo.android.db.cmd.truncate;

import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;

public class CmdTruncateWorkReports extends CmdTruncate {
    public CmdTruncateWorkReports() {
        super(WorkReportTable.TABLE_NAME);
    }
}
