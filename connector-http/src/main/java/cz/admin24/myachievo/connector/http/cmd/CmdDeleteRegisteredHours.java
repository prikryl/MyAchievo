package cz.admin24.myachievo.connector.http.cmd;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.admin24.myachievo.connector.http.PersistentConnection;

public class CmdDeleteRegisteredHours {
    private static final Logger       LOG               = LoggerFactory.getLogger(CmdDeleteRegisteredHours.class);
    private final String              dispatchUrlString = "https://timesheet.trask.cz/achievo/dispatch.php?";
    private final URL                 dispatchUrl;
    private final Map<String, String> params            = new HashMap<String, String>();


    public CmdDeleteRegisteredHours(String workReportId) {
        try {
            dispatchUrl = new URL(dispatchUrlString);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid url", e);
        }
        params.put("atkaction", "delete");
        params.put("atknodetype", "timereg.hours");
        params.put("atkselector", "hoursbase.id='" + workReportId + "'");
        params.put("confirm", "Yes");
    }


    public String execute(PersistentConnection connection) throws IOException {
        LOG.debug("deleting hours: {}", params);
        return connection.doPost(params, dispatchUrl);
    }
}
