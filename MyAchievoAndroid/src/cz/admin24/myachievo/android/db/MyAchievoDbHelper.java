package cz.admin24.myachievo.android.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.admin24.myachievo.android.db.cmd.get.CmdGet;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplace;

public class MyAchievoDbHelper extends SQLiteOpenHelper {
    private static final Logger LOG = LoggerFactory.getLogger(MyAchievoDbHelper.class);


    public MyAchievoDbHelper(Context context) {
        super(context, MyAchievoContract.DATABASE_NAME, null, MyAchievoContract.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String cmd : MyAchievoContract.SQL_CREATE_COMMANDS) {
            db.execSQL(cmd);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public <T> List<T> get(CmdGet<T> cmd) throws DatabaseException {
        SQLiteDatabase db = getReadableDatabase();
        try {
            return cmd.execute(db);
        } catch (RuntimeException e) {
            LOG.error("Failed to execute {}", cmd, e);
            throw new DatabaseException(cmd, e);
        } finally {
            db.close();
        }
    }


    public void update(CmdReplace cmd) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            cmd.execute(db);
            db.setTransactionSuccessful();
        } catch (RuntimeException e) {
            LOG.error("Failed to execute {}", cmd, e);
            throw new DatabaseException(cmd, e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
