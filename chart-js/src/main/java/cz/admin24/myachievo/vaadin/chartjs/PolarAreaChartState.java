package cz.admin24.myachievo.vaadin.chartjs;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PolarAreaChartState extends JavaScriptComponentState {

    private static final long serialVersionUID = 1L;

    public List<DataItem>     data             = new ArrayList<DataItem>();
    public Options            options          = new Options();

}
