package cz.admin24.myachievo.android.db.cmd.get;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class CmdGet<T> {

    private final Map<String, Integer> indexNameCacheMap = new HashMap<String, Integer>();


    public abstract List<T> execute(SQLiteDatabase db);


    protected Integer getInteger(Cursor cursor, String columnName) {
        Integer idx = getColumnIndex(cursor, columnName);
        if (cursor.isNull(idx)) {
            return null;
        }
        return cursor.getInt(idx);
    }


    protected Date getDate(Cursor cursor, String columnName) {
        Integer idx = getColumnIndex(cursor, columnName);
        if (cursor.isNull(idx)) {
            return null;
        }
        return new Date(cursor.getLong(idx));
    }


    protected String getString(Cursor cursor, String columnName) {
        Integer idx = getColumnIndex(cursor, columnName);
        if (cursor.isNull(idx)) {
            return null;
        }
        return cursor.getString(idx);
    }


    private Integer getColumnIndex(Cursor cursor, String columnName) {
        Integer idx = indexNameCacheMap.get(columnName);
        if (idx == null) {
            idx = cursor.getColumnIndex(columnName);
            indexNameCacheMap.put(columnName, idx);
        }
        return idx;
    }

}
