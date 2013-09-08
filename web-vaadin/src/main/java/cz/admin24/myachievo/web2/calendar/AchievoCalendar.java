package cz.admin24.myachievo.web2.calendar;

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

import cz.admin24.myachievo.web2.calendar.detail.EventDetailsWindow;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;

@Component
@Scope("prototype")
@VaadinView(AchievoCalendar.NAME)
public class AchievoCalendar extends VerticalLayout implements View {
    private static final long       serialVersionUID = 1L;
    public static final String      NAME             = "";
    private final Calendar          calendar         = new Calendar();

    @Autowired
    private AchievoConnectorWrapper achievoConnector;


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
                EventDetailsWindow eventDetailsWindow = new EventDetailsWindow((WorkReportEvent) event.getCalendarEvent());
                AchievoCalendar.this.getUI().addWindow(eventDetailsWindow);
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
