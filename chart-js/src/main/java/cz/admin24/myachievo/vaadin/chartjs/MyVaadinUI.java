package cz.admin24.myachievo.vaadin.chartjs;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = true, ui = MyVaadinUI.class, widgetset = "cz.admin24.myachievo.vaadin.chartjs.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }


    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        final PolarAreaChartComponent chart = new PolarAreaChartComponent();
        layout.addComponent(chart);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));

                PolarAreaChartState state = chart.getState();
                List<DataItem> data = state.data;
                data.clear();
                data.add(new DataItem(10, "red"));
                data.add(new DataItem(20, "blue"));
                data.add(new DataItem(30, "yellow"));
                data.add(new DataItem(Math.random() * 100, "black"));
            }
        });
        layout.addComponent(button);
    }

}
