package cz.admin24.myachievo.connector.http.cmd;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.admin24.myachievo.connector.http.EscapeUtils;
import cz.admin24.myachievo.connector.http.PersistentConnection;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;

public class CmdGetProjectPhases {
    private static final Logger  LOG                            = LoggerFactory.getLogger(CmdGetProjectPhases.class);

    // private static final String allProjectsUrlString =
    // "https://timesheet.trask.cz/achievo/dispatch.php?atknodetype=timereg.hours&atkaction=add&atkfp=&atkpartial=attribute.phaseid.refresh&projectid=project.id%3D%272145%27";
    private static final String  allProjectPhasesUrlStringStart = "https://timesheet.trask.cz/achievo/dispatch.php?atknodetype=timereg.hours&atkaction=add&atkfp=&atkpartial=attribute.phaseid.refresh&projectid=project.id%3D%27";
    private static final String  allProjectPhasesUrlStringStop  = "%27";
    private static final Pattern OPTION_PATTERN                 = Pattern.compile("(?ms)option value=\\\\\"phase.id='([0-9]+)'\\\\\" >(.*?)<");
    // private static final Pattern TD_PATTERN = Pattern.compile("(?ms)<td[^>]*>(.*?)</td>");
    private final URL            allProjectPhasesUrl;

    private final String         projectId;


    public CmdGetProjectPhases(String projectId) {
        this.projectId = projectId;
        try {
            allProjectPhasesUrl = new URL(allProjectPhasesUrlStringStart + projectId + allProjectPhasesUrlStringStop);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public List<ProjectPhase> execute(PersistentConnection connection) throws IOException {
        List<ProjectPhase> ret = new ArrayList<ProjectPhase>();
        String response = connection.doGet(allProjectPhasesUrl);
        LOG.trace("response:\n{}", response);

        Matcher m = OPTION_PATTERN.matcher(response);
        while (m.find()) {
            String id = m.group(1);
            String name = EscapeUtils.unescape(m.group(2));

            ProjectPhase p = new ProjectPhase(id, projectId, name);
            LOG.debug("Phase: {}", p);

            ret.add(p);
        }
        return ret;
    }

}
