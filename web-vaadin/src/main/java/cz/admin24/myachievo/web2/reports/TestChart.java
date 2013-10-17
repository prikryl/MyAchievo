package cz.admin24.myachievo.web2.reports;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickEvent;
import org.dussan.vaadin.dcharts.events.click.ChartDataClickHandler;
import org.dussan.vaadin.dcharts.events.mouseenter.ChartDataMouseEnterEvent;
import org.dussan.vaadin.dcharts.events.mouseenter.ChartDataMouseEnterHandler;
import org.dussan.vaadin.dcharts.events.mouseleave.ChartDataMouseLeaveEvent;
import org.dussan.vaadin.dcharts.events.mouseleave.ChartDataMouseLeaveHandler;
import org.dussan.vaadin.dcharts.events.rightclick.ChartDataRightClickEvent;
import org.dussan.vaadin.dcharts.events.rightclick.ChartDataRightClickHandler;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.DonutRenderer;

import cz.admin24.myachievo.web2.widgets.chart.Widget;

public class TestChart extends Widget {

    public TestChart() {
        DataSeries dataSeries = new DataSeries();
        dataSeries.newSeries()
            .add("a", 6)
            .add("b", 8)
            .add("c", 14)
            .add("d", 20);
        dataSeries.newSeries()
            .add("a", 8)
            .add("b", 12)
            .add("c", 6)
            .add("d", 9);

        SeriesDefaults seriesDefaults = new SeriesDefaults()
            .setRenderer(SeriesRenderers.DONUT)
            .setRendererOptions(
                new DonutRenderer()
                    .setSliceMargin(3)
                    .setStartAngle(-90));

        Options options = new Options()
            .setCaptureRightClick(true)
            .setSeriesDefaults(seriesDefaults);

        DCharts chart = new DCharts();

        chart.setEnableChartDataMouseEnterEvent(true);
        chart.setEnableChartDataMouseLeaveEvent(true);
        chart.setEnableChartDataClickEvent(true);
        chart.setEnableChartDataRightClickEvent(true);

        chart.addHandler(new ChartDataMouseEnterHandler() {
            @Override
            public void onChartDataMouseEnter(ChartDataMouseEnterEvent event) {
//                showNotification("CHART DATA MOUSE ENTER", event.getChartData());
            }
        });

        chart.addHandler(new ChartDataMouseLeaveHandler() {
            @Override
            public void onChartDataMouseLeave(ChartDataMouseLeaveEvent event) {
//                showNotification("CHART DATA MOUSE LEAVE", event.getChartData());
            }
        });

        chart.addHandler(new ChartDataClickHandler() {
            @Override
            public void onChartDataClick(ChartDataClickEvent event) {
//                showNotification("CHART DATA CLICK", event.getChartData());
            }
        });

        chart.addHandler(new ChartDataRightClickHandler() {
            @Override
            public void onChartDataRightClick(ChartDataRightClickEvent event) {
//                showNotification("CHART DATA RIGHT CLICK", event.getChartData());
            }
        });

        chart.setDataSeries(dataSeries)
            .setOptions(options)
            .show();
        addComponent(chart);

        chart.setHeight("300px");
        chart.setWidth("600px");
    }
}
