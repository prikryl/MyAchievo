package cz.admin24.myachievo.connector.http;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.admin24.myachievo.connector.http.dto.BaseObject;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;

@Test
public class AchievoProxyTest extends TestCase {
    private static final Logger LOG              = LoggerFactory.getLogger(AchievoProxyTest.class);
    private static final String USERNAME         = "pprikryl";
    private static final String PASSWORD_OK      = "UniC5701";
    private static final String PASSWORD_INVALID = "in_va?Li#d";


    @Test(expectedExceptions = AuthentizationException.class)
    public void testAuth_KO() throws AuthentizationException, IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.authentize(USERNAME, PASSWORD_INVALID);
    }


    @Test
    public void testAuth_OK() throws AuthentizationException, IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.authentize(USERNAME, PASSWORD_OK);
    }


    @Test(enabled = false)
    public void testLoadProjectsStructures() throws IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.setCredentials(USERNAME, PASSWORD_OK);
        // proxy.registerHours(new Date(), 8, 0, "2145", "10670", "2", "Implementace WS");
        List<Project> projects = proxy.getProjects();
        for (BaseObject project : projects) {
            List<ProjectPhase> phases = proxy.getPhases(project.getId());
            for (ProjectPhase phase : phases) {
                proxy.getActivities(project.getId(), phase.getId());
            }
        }
    }


    @Test(enabled = true)
    public void testLoadWorkReport() throws IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.setCredentials(USERNAME, PASSWORD_OK);
        Calendar c = Calendar.getInstance();
        Date to = c.getTime();

        c.add(Calendar.WEEK_OF_YEAR, -1);
        Date from = c.getTime();

        proxy.getHours(from, to);
    }


    @Test(enabled = true)
    public void testLoadWorkReport_withoutData() throws IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.setCredentials(USERNAME, PASSWORD_OK);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, 1);
        Date to = c.getTime();

        c.add(Calendar.WEEK_OF_YEAR, 1);
        Date from = c.getTime();

        List<WorkReport> hours = proxy.getHours(from, to);

        Assert.assertEquals(hours, Collections.EMPTY_LIST);
    }


    @Test(enabled = false)
    public void testLogWork() throws IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.setCredentials(USERNAME, PASSWORD_OK);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -18);

        List<WorkReport> registerHours = proxy.registerHours(c.getTime(), 8, 0, "2145", "10670", "2", "vyvoj 3.4 pro cs");

        LOG.info("Registred hours:\n{}", registerHours);
    }


    @Test(enabled = false)
    public void testEditWork() throws IOException {
        AchievoConnector proxy = new AchievoConnectorImpl();
        proxy.setCredentials(USERNAME, PASSWORD_OK);
        Calendar c = Calendar.getInstance();
        // c.add(Calendar.DAY_OF_YEAR, -18);

        List<WorkReport> registerHours = proxy.updateRegiteredHours("745006_PICK_NEW ONE", c.getTime(), 8, 0, "2145", "10670", "2", "vyvoj 3.4 pro cs");

        LOG.info("Registred hours:\n{}", registerHours);
    }

}
