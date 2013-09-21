package cz.admin24.myachievo.web2.widgets.chart;

import java.util.Date;
import java.util.List;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.DataLabels;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import cz.admin24.myachievo.connector.http.dto.WorkReport;

public class ReportedHoursByProjectChart extends Widget {
    private final DCharts chart = new DCharts();
    private final Options options;


    public ReportedHoursByProjectChart() {
        super();
        setCaption("This month report...");
        addComponent(chart);

        // SeriesDefaults seriesDefaults = new SeriesDefaults()
        // .setRenderer(SeriesRenderers.DONUT)
        // .setRendererOptions(
        // new DonutRenderer()
        // .setSliceMargin(3)
        // .setStartAngle(-90)
        // .setShowDataLabels(true)
        // .setDataLabels(DataLabels.PERCENT));
        SeriesDefaults seriesDefaults = new SeriesDefaults()
                .setRenderer(SeriesRenderers.PIE)
                .setRendererOptions(
                        new PieRenderer()
                                .setShowDataLabels(true)
                                .setDataLabels(DataLabels.LABEL));

        Legend legend = new Legend()
                .setShow(true)
                .setPlacement(LegendPlacements.OUTSIDE_GRID)
                .setLocation(LegendLocations.WEST);

        Highlighter highlighter = new Highlighter()
                .setShow(true)
                .setShowTooltip(true)
                .setTooltipAlwaysVisible(true)
                .setKeepTooltipInsideChart(true);

        options = new Options()
                .setSeriesDefaults(seriesDefaults)
                .setLegend(legend)
                .setHighlighter(highlighter);

        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);

        // chart.addHandler(new ChartDataMouseEnterHandler() {
        // @Override
        // public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
        // showNotification("CHART DATA MOUSE ENTER", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataMouseLeaveHandler() {
        // @Override
        // public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
        // showNotification("CHART DATA MOUSE LEAVE", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataClickHandler() {
        // @Override
        // public void onChartDataClick(ChartDataClickEvent event) {
        // showNotification("CHART DATA CLICK", event.getChartData());
        // }
        // });
        //
        // chart.addHandler(new ChartDataRightClickHandler() {
        // @Override
        // public void onChartDataRightClick(ChartDataRightClickEvent event) {
        // showNotification("CHART DATA RIGHT CLICK", event.getChartData());
        // }
        // });

        // refresh();

    }


    public void refresh(List<WorkReport> workReports, Date month) {

        Multimap<String, WorkReport> map = ArrayListMultimap.create();
        Multiset<String> durations = HashMultiset.create();

        // sort out reports mased on project
        for (WorkReport workReport : workReports) {
            map.put(workReport.getProject(), workReport);
            durations.add(workReport.getProject(), workReport.getHours() * 60 + workReport.getMinutes());
        }

        DataSeries dataSeries = new DataSeries();
        dataSeries.newSeries();
        for (String project : durations.elementSet()) {
            dataSeries.add(project, durations.count(project));
        }
        // dataSeries.newSeries()
        // .add("a", 6)
        // .add("b", 8)
        // .add("c", 14)
        // .add("d", 20);
        // dataSeries.newSeries()
        // .add("a", 8)
        // .add("b", 12)
        // .add("c", 6)
        // .add("d", 9);
        //
        chart.setDataSeries(dataSeries)
                .setOptions(options)
                .show();
    }

}
