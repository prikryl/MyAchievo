package cz.admin24.myachievo.web2.calendar;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResizeHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.WeekClickHandler;

import cz.admin24.myachievo.connector.http.dto.PhaseActivity;
import cz.admin24.myachievo.connector.http.dto.Project;
import cz.admin24.myachievo.connector.http.dto.ProjectPhase;
import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.calendar.CalendarUrl.CalendarViewType;
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
    private final HorizontalLayout  buttonsLayout    = new HorizontalLayout();
    private final Button            nextBtn          = new Button("Next");
    private final Button            prevBtn          = new Button("Prev");
    private final Link              todayBtn         = new Link("Today", null);
    private final Link              dailyBtn         = new Link("Daily", null);
    private final Link              weeklyBtn        = new Link("Weekly", null);
    private final Link              monthlyBtn       = new Link("Monthly", null);

    @Autowired
    private AchievoConnectorWrapper achievoConnector;
    @Autowired
    private ProjectsCache           projectsCache;


    // @Autowired
    // private Navigator navigator;

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
                    protected void onEventChanged() {
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
                    protected void onEventChanged() {
                        calendar.markAsDirty();
                    }
                };
                getUI().addWindow(eventDetailsWindow);

            }
        });

        calendar.setHandler(new BackwardHandler() {

            @Override
            public void backward(BackwardEvent event) {
                CalendarUrl url = getUrl();
                url.prev();
                navigateTo(url);
            }
        });

        calendar.setHandler(new ForwardHandler() {

            @Override
            public void forward(ForwardEvent event) {
                CalendarUrl url = getUrl();
                url.next();
                navigateTo(url);
            }
        });

        calendar.setHandler(new WeekClickHandler() {

            @Override
            public void weekClick(WeekClick event) {
                java.util.Calendar c = java.util.Calendar.getInstance(UI.getCurrent().getLocale());
                c.set(java.util.Calendar.YEAR, event.getYear());
                c.set(java.util.Calendar.WEEK_OF_YEAR, event.getWeek());
                CalendarUrl url = getUrl();
                url.setDate(c.getTime());
                url.setType(CalendarViewType.WEEK);
                navigateTo(url);
            }
        });

        calendar.setHandler(new DateClickHandler() {

            @Override
            public void dateClick(DateClickEvent event) {
                CalendarUrl url = getUrl();
                url.setDate(event.getDate());
                url.setType(CalendarViewType.DAY);
                navigateTo(url);
            }
        });

        prevBtn.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                CalendarUrl url = getUrl();
                url.prev();
                navigateTo(url);
            }
        });

        nextBtn.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                CalendarUrl url = getUrl();
                url.next();
                navigateTo(url);
            }
        });

        // calendar.set
    }


    private void localize() {
        // TODO Auto-generated method stub

    }


    private void css() {
        setSizeFull();

        setExpandRatio(calendar, 1);

        calendar.setSizeFull();
        buttonsLayout.setWidth("100%");

        setMargin(true);
        setSpacing(true);
        buttonsLayout.setSpacing(true);

        buttonsLayout.setComponentAlignment(prevBtn, Alignment.MIDDLE_LEFT);
        buttonsLayout.setComponentAlignment(todayBtn, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(dailyBtn, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(weeklyBtn, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(monthlyBtn, Alignment.MIDDLE_CENTER);
        buttonsLayout.setComponentAlignment(nextBtn, Alignment.MIDDLE_RIGHT);

    }


    private void buildLayout() {
        addComponent(buttonsLayout);
        addComponent(calendar);

        buttonsLayout.addComponent(prevBtn);
        buttonsLayout.addComponent(monthlyBtn);
        buttonsLayout.addComponent(weeklyBtn);
        buttonsLayout.addComponent(dailyBtn);
        buttonsLayout.addComponent(todayBtn);
        buttonsLayout.addComponent(nextBtn);
    }


    @Override
    public void enter(ViewChangeEvent event) {
        calendar.setEventProvider(new AchievoEventProvider(achievoConnector));
        String parameters = event.getParameters();
        CalendarUrl url = new CalendarUrl(parameters);
        if (StringUtils.isBlank(parameters)) {
            getUI().getNavigator().navigateTo(new CalendarUrl().toFragment());
            return;
        }

        calendar.setStartDate(url.getStartDate());
        calendar.setEndDate(url.getEndDate());

        refresh();
    }


    private void refresh() {
        CalendarUrl url = getUrl();

        url.setType(CalendarViewType.DAY);
        dailyBtn.setResource(new ExternalResource(url.toUrl()));

        url.setType(CalendarViewType.WEEK);
        weeklyBtn.setResource(new ExternalResource(url.toUrl()));

        url.setType(CalendarViewType.MONTH);
        monthlyBtn.setResource(new ExternalResource(url.toUrl()));

        url = new CalendarUrl();
        todayBtn.setResource(new ExternalResource(url.toUrl()));

    }


    private CalendarUrl getUrl() {
        return new CalendarUrl(Page.getCurrent());
    }


    private void navigateTo(CalendarUrl url) {
        UI.getCurrent().getNavigator().navigateTo(url.toFragment());
    }
}
