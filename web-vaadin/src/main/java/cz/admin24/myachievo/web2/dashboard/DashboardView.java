package cz.admin24.myachievo.web2.dashboard;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
@Component
@Scope("prototype")
@VaadinView(DashboardView.NAME)
public class DashboardView extends VerticalLayout implements View {

    public static final String NAME = "dashboard";


    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
