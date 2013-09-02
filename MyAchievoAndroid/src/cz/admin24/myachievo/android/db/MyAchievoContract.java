package cz.admin24.myachievo.android.db;

import android.provider.BaseColumns;

public class MyAchievoContract {
    public static final String   DATABASE_NAME       = "MyAchievo.db";
    public static final Integer  DATABASE_VERSION    = 1;
    public static final String[] SQL_CREATE_COMMANDS = new String[] { WorkReportTable.SQL_CREATE, ProjectTable.SQL_CREATE, ProjectPhaseTable.SQL_CREATE, PhaseActivityTable.SQL_CREATE };


    protected MyAchievoContract() {
    }

    // ----------------------------
    // ----------------------------
    // ----------------------------
    public static abstract class WorkReportTable implements BaseColumns {
        public static final String TABLE_NAME           = "WorkReport";
        public static final String COLUMN_NAME_PROJECT  = "project";
        public static final String COLUMN_NAME_PHASE    = "phase";
        public static final String COLUMN_NAME_ACTIVITY = "activity";
        public static final String COLUMN_NAME_REMARK   = "remark";
        public static final String COLUMN_NAME_DATE     = "date";
        public static final String COLUMN_NAME_HOURS    = "hours";
        public static final String COLUMN_NAME_MINUTES  = "minutes";
        //
        public static final String SQL_CREATE           = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER )",
                                                                TABLE_NAME, _ID, COLUMN_NAME_PROJECT, COLUMN_NAME_PHASE, COLUMN_NAME_ACTIVITY, COLUMN_NAME_REMARK, COLUMN_NAME_DATE,
                                                                COLUMN_NAME_HOURS, COLUMN_NAME_MINUTES);
    }

    // ----------------------------
    // ----------------------------
    // ----------------------------
    public static abstract class ProjectTable implements BaseColumns {
        public static final String TABLE_NAME              = "Project";
        public static final String COLUMN_NAME_ID          = "id";
        public static final String COLUMN_NAME_CODE        = "code";
        public static final String COLUMN_NAME_NAME        = "name";
        public static final String COLUMN_NAME_COORDINATOR = "coordinator";
        public static final String COLUMN_NAME_BEGIN       = "begin";
        public static final String COLUMN_NAME_END         = "end";
        public static final String SQL_CREATE              = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s , %s INTEGER, %s INTEGER )",
                                                                   TABLE_NAME, _ID, COLUMN_NAME_ID, COLUMN_NAME_CODE, COLUMN_NAME_NAME, COLUMN_NAME_COORDINATOR, COLUMN_NAME_BEGIN, COLUMN_NAME_END);
    }

    public static abstract class ProjectPhaseTable implements BaseColumns {
        public static final String TABLE_NAME             = "ProjectPhase";
        public static final String COLUMN_NAME_ID         = "id";
        public static final String COLUMN_NAME_PROJECT_ID = "project_id";
        public static final String COLUMN_NAME_NAME       = "name";
        public static final String SQL_CREATE             = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT )",
                                                                  TABLE_NAME, _ID, COLUMN_NAME_ID, COLUMN_NAME_PROJECT_ID, COLUMN_NAME_NAME);
    }

    public static abstract class PhaseActivityTable implements BaseColumns {
        public static final String TABLE_NAME           = "PhaseActivity";
        public static final String COLUMN_NAME_ID       = "id";
        public static final String COLUMN_NAME_PHASE_ID = "project_id";
        public static final String COLUMN_NAME_NAME     = "name";
        public static final String SQL_CREATE           = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT )",
                                                                TABLE_NAME, _ID, COLUMN_NAME_ID, COLUMN_NAME_PHASE_ID, COLUMN_NAME_NAME);
    }

    // ----------------------------
    // ----------------------------
    // ----------------------------

}
