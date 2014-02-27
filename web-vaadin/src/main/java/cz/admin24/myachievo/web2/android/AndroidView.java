package cz.admin24.myachievo.web2.android;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

//@Component
//@Scope("prototype")
//@VaadinView(AndroidView.NAME)
public class AndroidView extends VerticalLayout implements View {

    public static final String NAME        = "android";

    // private final BrowserFrame browserFrame = new BrowserFrame(null, new
    // ExternalResource("https://play.google.com/store/apps/details?id=cz.admin24.myachievo.android"));
    private final CustomLayout androidLink = new CustomLayout("myAchievo-googlePlay-link");


    public AndroidView() {
        addComponent(androidLink);
        setSizeFull();
        setComponentAlignment(androidLink, Alignment.MIDDLE_CENTER);
        androidLink.setSizeUndefined();

    }


    @Override
    public void enter(ViewChangeEvent event) {
        // browserFrame.op
    }

}
