package cz.admin24.myachievo.android.db.cmd.get;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cz.admin24.myachievo.android.db.MyAchievoContract.WorkReportTable;

public class CmdGetLastRemarks extends CmdGet<String> {

    @Override
    public List<String> execute(SQLiteDatabase db) {
        LinkedHashSet<String> ret = new LinkedHashSet<String>();

        Cursor cursor = db.query(WorkReportTable.TABLE_NAME, new String[] { WorkReportTable.COLUMN_NAME_REMARK }, null, null, null, null, WorkReportTable.COLUMN_NAME_DATE + " DESC");
        try {
            boolean found = cursor.moveToFirst();
            while (found) {
                String str = getString(cursor, WorkReportTable.COLUMN_NAME_REMARK);
                if (!StringUtils.isBlank(str)) {
                    ret.add(str);
                }

                found = cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return new ArrayList<String>(ret);
    }

}
