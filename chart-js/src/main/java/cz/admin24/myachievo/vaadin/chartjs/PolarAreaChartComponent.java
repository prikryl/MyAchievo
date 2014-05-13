package cz.admin24.myachievo.vaadin.chartjs;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

@StyleSheet({
        "public/chartjs/Chartjs.css",
})
@JavaScript({
        "public/chartjs/Chart.js",
        "PolarAreaChartComponent_connector.js"
})
public class PolarAreaChartComponent extends AbstractJavaScriptComponent {

    private static final long serialVersionUID = 1L;


    public PolarAreaChartComponent() {
        super();
    }


    @Override
    protected PolarAreaChartState getState() {
        return (PolarAreaChartState) super.getState();
    }

}
