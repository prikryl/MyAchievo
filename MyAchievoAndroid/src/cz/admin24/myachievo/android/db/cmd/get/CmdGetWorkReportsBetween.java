package cz.admin24.myachievo.android.db.cmd.get;

import java.util.Date;

import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;

public class CmdGetWorkReportsBetween extends CmdGetWorkReports {
    private final Date from;
    private final Date to;


    public CmdGetWorkReportsBetween(Date from, Date to) {
        super();
        this.from = from;
        this.to = to;
    }


    @Override
    protected String[] getSelectionArgs() {
        return new String[] { "" + from.getTime(), "" + to.getTime() };
    }


    @Override
    protected String getSelection() {
        return WorkReportTable.COLUMN_NAME_DATE + " BETWEEN ? AND ? ";
    }

}