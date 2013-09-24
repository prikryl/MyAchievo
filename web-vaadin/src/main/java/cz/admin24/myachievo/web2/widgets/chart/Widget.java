package cz.admin24.myachievo.web2.widgets.chart;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class Widget extends CssLayout {
    private final VerticalLayout content = new VerticalLayout();


    public Widget() {
        addStyleName("layout-panel");
        // setSizeFull();

        Button configure = new Button();
        configure.addStyleName("configure");
        configure.addStyleName("icon-cog");
        configure.addStyleName("icon-only");
        configure.addStyleName("borderless");
        configure.setDescription("Configure");
        configure.addStyleName("small");
        configure.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        super.addComponent(configure);
        super.addComponent(content);

        // panel.addComponent(content);
        // return panel;

        setWidth("100%");
        content.setWidth("100%");
        // setSizeFull();
        // content.setSizeFull();

    }


    @Override
    public void addComponent(Component c) {
        content.addComponent(c);
    }


    @Override
    public void setCaption(String caption) {
        content.setCaption(caption);
    }
}
