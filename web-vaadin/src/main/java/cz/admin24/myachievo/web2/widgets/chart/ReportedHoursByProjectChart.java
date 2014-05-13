package cz.admin24.myachievo.web2.widgets.chart;

import java.util.Date;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;

import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class ReportedHoursByProjectChart extends Widget {
    /**
     *
     */
    private static final long    serialVersionUID = 1L;

    private final Chart          chart            = new Chart(ChartType.PIE);
    private final Configuration  conf             = chart.getConfiguration();
    private final PlotOptionsPie plotOptions      = new PlotOptionsPie();
    private final Labels         dataLabels       = new Labels();


    public ReportedHoursByProjectChart(String caption) {
        super();
        setCaption("Work statistics");

        conf.setTitle(caption);

        initTooltip();

        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
//        plotOptions.setShowInLegend(true);

        dataLabels.setEnabled(true);
        conf.setPlotOptions(plotOptions);

        addComponent(chart);
        // chart.setHeight("300px");
        // chart.setWidth("600px");

    }


    private void initTooltip() {
        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
//        tooltip.setPointFormat("{point.y/8} hours: {point.percentage}%");
//        return 'The value for <b>' + this.x + '</b> is <b>' + this.y + '</b>, in series '+ this.series.name
        tooltip.setFormatter("return '<b>'+ this.point.name +'</b><br/>'"
                + "+ Highcharts.numberFormat(this.y/60, 2) + ' hours <br/>'"
                + "+ Highcharts.numberFormat(this.percentage, 2) +' %';");
        conf.setTooltip(tooltip);
    }


    public void refresh(List<WorkReport> workReports, Date month) {
        Multimap<String, WorkReport> map = ArrayListMultimap.create();
        Multiset<String> durations = HashMultiset.create();

        // sort out reports mased on project
        for (WorkReport workReport : workReports) {
            map.put(workReport.getProject(), workReport);
            durations.add(workReport.getProject(), workReport.getHours() * 60 + workReport.getMinutes());
        }

        DataSeries series = new DataSeries();
        for (String project : durations.elementSet()) {
            series.add(new DataSeriesItem(project, durations.count(project)));
        }
        // DataSeriesItem chrome = new DataSeriesItem("Chrome", 12.8);
        // chrome.setSliced(true);
        // chrome.setSelected(true);
        // series.add(chrome);
        // series.add(new DataSeriesItem("Safari", 8.5));
        // series.add(new DataSeriesItem("Opera", 6.2));
        // series.add(new DataSeriesItem("Others", 0.7));
        conf.setSeries(series);

        chart.drawChart(conf);
    }

}
