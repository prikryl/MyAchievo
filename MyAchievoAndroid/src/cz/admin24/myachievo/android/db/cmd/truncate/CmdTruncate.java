package cz.admin24.myachievo.android.db.cmd.truncate;

import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplace;

public abstract class CmdTruncate extends CmdReplace {

    protected final String tableName;


    public CmdTruncate(String tableName) {
        super();
        this.tableName = tableName;
    }


    @Override
    public void execute(SQLiteDatabase db) {
        db.delete(tableName, null, null);
    }

}