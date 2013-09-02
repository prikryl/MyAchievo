package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class CmdGetOrderedNames extends CmdGet<String> {

    protected final String columnName;


    public CmdGetOrderedNames(String columnName) {
        super();
        this.columnName = columnName;
    }


    @Override
    public List<String> execute(SQLiteDatabase db) {
        List<String> list = new ArrayList<String>();
        Cursor cursor = createCursor(db);
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String str = getString(cursor, columnName);
                list.add(str);

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        // newes project names as first
        Collections.reverse(list);
        // remove duplicates
        LinkedHashSet<String> uniq = new LinkedHashSet<String>(list);

        return new ArrayList<String>(uniq);
    }


    protected abstract Cursor createCursor(SQLiteDatabase db);

}