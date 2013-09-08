package cz.admin24.myachievo.web2.calendar;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;

public class AchievoEventProvider implements CalendarEventProvider {
    private static final Logger           LOG              = LoggerFactory.getLogger(AchievoEventProvider.class);

    private static final long             serialVersionUID = 1L;
    //
    private final AchievoConnectorWrapper achievoConnector;


    public AchievoEventProvider(AchievoConnectorWrapper achievoConnector) {
        this.achievoConnector = achievoConnector;
    }


    @Override
    public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
        List<CalendarEvent> ret = new LinkedList<CalendarEvent>();
        List<WorkReport> hours = Lists.newArrayList();
        hours = achievoConnector.getHours(startDate, endDate);

        for (WorkReport report : hours) {
            ret.add(new WorkReportEvent(report));
        }

        return ret;
    }

}
