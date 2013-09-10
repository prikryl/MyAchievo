package cz.admin24.myachievo.web2.calendar;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.calendar.detail.EventDetailsWindow;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;
import cz.admin24.myachievo.web2.service.ProjectsCache;

@Component
@Scope("prototype")
@VaadinView(AchievoCalendar.NAME)
public class AchievoCalendar extends VerticalLayout implements View {
    private static final long       serialVersionUID = 1L;
    public static final String      NAME             = "";
    private final Calendar          calendar         = new Calendar();

    @Autowired
    private AchievoConnectorWrapper achievoConnector;
    @Autowired
    private ProjectsCache           projectsCache;


    public AchievoCalendar() {
        buildLayout();
        configure();
        css();
        localize();
        // DaoAuthenticationProvider
    }


    private void configure() {
        calendar.setHandler(new EventClickHandler() {

            @Override
            public void eventClick(EventClick event) {
                WorkReportEvent calendarEvent = (WorkReportEvent) event.getCalendarEvent();
                EventDetailsWindow eventDetailsWindow = new EventDetailsWindow(calendarEvent.getWorkReport()) {
                    @Override
                    public void commit() {
                        super.commit();
                        calendar.markAsDirty();
                    }
                };
                AchievoCalendar.this.getUI().addWindow(eventDetailsWindow);
            }
        });

        calendar.setHandler(new EventResizeHandler() {

            @Override
            public void eventResize(EventResize event) {
                WorkReportEvent wre = (WorkReportEvent) event.getCalendarEvent();
                WorkReport r = wre.getWorkReport();

                DateTime start = new DateTime(event.getNewStart());
                DateTime newEnd = new DateTime(event.getNewEnd());
                int minutesBetween = Minutes.minutesBetween(start, newEnd).getMinutes();
                minutesBetween = (int) Math.round(minutesBetween / 15.0) * 15;
                Integer hours = minutesBetween / 60;
                Integer minutes = minutesBetween % 60;

                Project project = projectsCache.getProjectByName(r.getProject());
                ProjectPhase phase = projectsCache.getPhaseByName(r.getPhase(), project);
                PhaseActivity activity = projectsCache.getActivityByName(r.getActivity(), phase, project);

                achievoConnector.updateRegiteredHours(r.getId(), start.toDate(), hours, minutes, project.getId(), phase.getId(), activity.getId(), r.getRemark());
                calendar.markAsDirty();
            }
        });

        calendar.setHandler(new EventMoveHandler() {

            @Override
            public void eventMove(MoveEvent event) {
                WorkReportEvent wre = (WorkReportEvent) event.getCalendarEvent();
                WorkReport r = wre.getWorkReport();

                Date newStart = event.getNewStart();
                if (DateUtils.isSameDay(r.getDate(), newStart)) {
                    // NOP
                    return;
                }

                Project project = projectsCache.getProjectByName(r.getProject());
                ProjectPhase phase = projectsCache.getPhaseByName(r.getPhase(), project);
                PhaseActivity activity = projectsCache.getActivityByName(r.getActivity(), phase, project);

                achievoConnector.updateRegiteredHours(r.getId(), newStart, r.getHours(), r.getMinutes(), project.getId(), phase.getId(), activity.getId(), r.getRemark());
                calendar.markAsDirty();
            }
        });

        calendar.setHandler(new RangeSelectHandler() {

            @Override
            public void rangeSelect(RangeSelectEvent event) {

                DateTime start = new DateTime(event.getStart());
                DateTime newEnd = new DateTime(event.getEnd());
                int minutesBetween = Minutes.minutesBetween(start, newEnd).getMinutes();
                minutesBetween = (int) Math.round(minutesBetween / 15.0) * 15;
                Integer hours = minutesBetween / 60;
                Integer minutes = minutesBetween % 60;

                WorkReport workReport = new WorkReport(null, start.toDate(), null, null, null, null, hours, minutes);

                EventDetailsWindow eventDetailsWindow = new EventDetailsWindow(workReport) {
                    @Override
                    public void commit() {
                        super.commit();
                        calendar.markAsDirty();
                    }
                };
                getUI().addWindow(eventDetailsWindow);

            }
        });
    }


    private void localize() {
        // TODO Auto-generated method stub

    }


    private void css() {
        setSizeFull();
        calendar.setSizeFull();

    }


    private void buildLayout() {
        addComponent(calendar);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        calendar.setEventProvider(new AchievoEventProvider(achievoConnector));
    }
}
