package cz.admin24.myachievo.web2.base;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class BaseLayout extends HorizontalLayout {
    private final SideBar   sideBar = new SideBar();
    private final CssLayout content = new CssLayout();


    public BaseLayout() {
        addComponent(sideBar);
        addComponent(content);

        setExpandRatio(content, 1);
        content.setSizeFull();
        setSizeFull();

        content.addStyleName("content");
    }


    public ComponentContainer getContent() {
        return content;
    }

}
