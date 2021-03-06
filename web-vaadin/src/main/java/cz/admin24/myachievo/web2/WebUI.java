package cz.admin24.myachievo.web2;

import org.springframework.security.access.AccessDeniedException;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import cz.admin24.myachievo.web2.android.AndroidView;
import cz.admin24.myachievo.web2.base.BaseLayout;
import cz.admin24.myachievo.web2.calendar.CalendarView;
import cz.admin24.myachievo.web2.dashboard.DashboardView;
import cz.admin24.myachievo.web2.reports.ReportsView;

@Theme("dashboard")
@SuppressWarnings("serial")
// @Component("webUI")
// @Scope("prototype")
public class WebUI extends UI implements ErrorHandler {
    // private final AchievoCalendar achievoCalendar = new AchievoCalendar();
    private final BaseLayout baseLayout = new BaseLayout();
    private final Navigator  navigator;


    public WebUI() {
        UI.setCurrent(this);
        navigator = new Navigator(this, baseLayout.getContent());
        navigator.addView(CalendarView.NAME, new CalendarView());
        navigator.addView(DashboardView.NAME, new DashboardView());
        navigator.addView(ReportsView.NAME, ReportsView.class);
        navigator.addView(AndroidView.NAME, new AndroidView());
        // DiscoveryNavigator navigator = new DiscoveryNavigator(this, baseLayout.getContent());
        VaadinSession.getCurrent().setErrorHandler(this);
        setContent(baseLayout);
        setSizeFull();
        setStyleName(Reindeer.LAYOUT_WHITE);

        // Navigator navigator = new Navigator(this, this);

        // navigator.addView(AchievoCalendar.NAME, AchievoCalendar.class);

        // #message adds a label with whatever it receives as a parameter
        // navigator.addView(MessageView.NAME, new MessageView());
        // @VaadinView(TestView.NAME)...
    }


    //
    // @WebServlet(value = "/*", asyncSupported = true)
    // @VaadinServletConfiguration(productionMode = false, ui = WebUI.class, widgetset =
    // "cz.admin24.myachievo.web2.AppWidgetSet")
    // public static class Servlet extends VaadinServlet {
    // }

    @Override
    protected void init(VaadinRequest request) {
        // final VerticalLayout layout = new VerticalLayout();
        // layout.setMargin(true);
        // setContent(layout);
        //
        // Button button = new Button("Click Me");
        // button.addClickListener(new Button.ClickListener() {
        // public void buttonClick(ClickEvent event) {
        // layout.addComponent(new Label("Thank you for clicking"));
        // }
        // });
        // layout.addComponent(button);
        // layout.addComponent(achievoCalendar);
    }


    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        // connector event
        if (event.getThrowable().getCause() instanceof AccessDeniedException)
        {
            AccessDeniedException accessDeniedException = (AccessDeniedException) event.getThrowable().getCause();
            Notification.show(accessDeniedException.getMessage(), Notification.Type.ERROR_MESSAGE);

            // Cleanup view. Now Vaadin ignores errors and always shows the view. :-(
            // since beta10
            setContent(null);
            return;
        }

        // Error on page load. Now it doesn't work. User sees standard error page.
        if (event.getThrowable() instanceof AccessDeniedException)
        {
            AccessDeniedException exception = (AccessDeniedException) event.getThrowable();

            Label label = new Label(exception.getMessage());
            label.setWidth(-1, Unit.PERCENTAGE);

            Link goToMain = new Link("Go to main", new ExternalResource("/"));

            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(label);
            layout.addComponent(goToMain);
            layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            layout.setComponentAlignment(goToMain, Alignment.MIDDLE_CENTER);

            VerticalLayout mainLayout = new VerticalLayout();
            mainLayout.setSizeFull();
            mainLayout.addComponent(layout);
            mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

            setContent(mainLayout);
            Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);
            return;
        }

        DefaultErrorHandler.doDefault(event);
    }

}
