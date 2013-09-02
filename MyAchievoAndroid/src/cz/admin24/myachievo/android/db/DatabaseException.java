package cz.admin24.myachievo.android.db;

import cz.admin24.myachievo.android.db.cmd.get.CmdGet;
import cz.admin24.myachievo.android.db.cmd.replace.CmdReplace;

public class DatabaseException extends RuntimeException {

    public DatabaseException(CmdReplace cmd, Exception e) {
        super(e);
    }


    public DatabaseException(CmdGet cmd, RuntimeException e) {
        super(e);
    }

}
