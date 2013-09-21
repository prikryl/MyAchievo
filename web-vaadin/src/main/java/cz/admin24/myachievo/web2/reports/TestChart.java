package cz.admin24.myachievo.web2.reports;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.DataLabels;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Legend;
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
                    .setStartAngle(-90)
                    .setShowDataLabels(true)
                    .setDataLabels(DataLabels.VALUE));

        Legend legend = new Legend()
            .setShow(true)
            .setPlacement(LegendPlacements.OUTSIDE_GRID)
            .setLocation(LegendLocations.WEST);

        Options options = new Options()
            .setSeriesDefaults(seriesDefaults)
            .setLegend(legend);

        DCharts chart = new DCharts()
            .setDataSeries(dataSeries)
            .setOptions(options)
            .show();
        addComponent(chart);
    }
}
