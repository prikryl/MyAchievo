package cz.admin24.myachievo.web2.calendar;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@VaadinView(AchievoCalendar.NAME)
public class AchievoCalendar extends VerticalLayout implements View {
    private static final long  serialVersionUID = 1L;
    public static final String NAME             = "";
    private final Calendar     calendar         = new Calendar();


    public AchievoCalendar() {
        buildLayout();
        css();
        localize();
//        DaoAuthenticationProvider
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
        // TODO Auto-generated method stub

    }
}
