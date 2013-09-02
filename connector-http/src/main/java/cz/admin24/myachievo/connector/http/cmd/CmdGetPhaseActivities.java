package cz.admin24.myachievo.connector.http.cmd;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.admin24.myachievo.connector.http.EscapeUtils;
import cz.admin24.myachievo.connector.http.PersistentConnection;
import cz.admin24.myachievo.connector.http.dto.PhaseActivity;

public class CmdGetPhaseActivities {
    private static final Logger  LOG                              = LoggerFactory.getLogger(CmdGetPhaseActivities.class);

    // private static final String allProjectsUrlString =
    // "https://timesheet.trask.cz/achievo/dispatch.php?atknodetype=timereg.hours&atkaction=add&atkfp=&atkpartial=attribute.phaseid.refresh&projectid=project.id%3D%272145%27";
    private static final String  allProjectPhasesUrlStringPattern = "https://timesheet.trask.cz/achievo/dispatch.php?atknodetype=timereg.hours&atkaction=add&atkfp=&atkpartial=attribute.activityid.refresh&projectid=project.id%3D'$PROJECT_ID'&phaseid=phase.id%3D'$PHASE_ID'";
    private static final Pattern OPTION_PATTERN                   = Pattern.compile("(?ms)option value=\\\\\"activity.id='([0-9]+)'\\\\\" >(.*?)<");
    // private static final Pattern TD_PATTERN = Pattern.compile("(?ms)<td[^>]*>(.*?)</td>");
    private final URL            allPhasesActivitiesUrl;

    private final String         projectId;
    private final String         phaseId;


    public CmdGetPhaseActivities(String projectId, String phaseId) {
        this.projectId = projectId;
        this.phaseId = phaseId;
        try {
            allPhasesActivitiesUrl = new URL(StringUtils.replaceEach(allProjectPhasesUrlStringPattern, new String[] { "$PROJECT_ID", "$PHASE_ID" }, new String[] { projectId, phaseId }));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public List<PhaseActivity> execute(PersistentConnection connection) throws IOException {
        List<PhaseActivity> ret = new ArrayList<PhaseActivity>();
        String response = connection.doGet(allPhasesActivitiesUrl);
        LOG.trace("response:\n{}", response);

        Matcher m = OPTION_PATTERN.matcher(response);
        while (m.find()) {
            String id = m.group(1);
            String name = EscapeUtils.unescape(m.group(2));

            PhaseActivity p = new PhaseActivity(id, phaseId, name);
            LOG.debug("Phase: {}", p);

            ret.add(p);
        }
        return ret;
    }

}
