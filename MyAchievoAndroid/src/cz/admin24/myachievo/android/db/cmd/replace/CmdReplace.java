package cz.admin24.myachievo.android.db.cmd.replace;

import java.util.Date;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public abstract class CmdReplace {
    public abstract void execute(SQLiteDatabase db);


    protected void bind(SQLiteStatement statement, int index, String value) {
        if (value == null) {
            statement.bindNull(index);
        } else {
            statement.bindString(index, value);
        }
    }


    protected void bind(SQLiteStatement statement, int index, Date value) {
        if (value == null) {
            statement.bindNull(index);
        } else {
            statement.bindLong(index, value.getTime());
        }
    }


    protected void bind(SQLiteStatement statement, int index, Integer value) {
        if (value == null) {
            statement.bindNull(index);
        } else {
            statement.bindLong(index, value);
        }
    }
}
