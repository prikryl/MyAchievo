package cz.admin24.myachievo.web2.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.google.common.collect.Lists;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.SpringUtils;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;
import cz.admin24.myachievo.web2.widgets.chart.ReportedHoursByProjectChart;
import cz.admin24.myachievo.web2.widgets.chart.Widget;

public class ReportsView extends VerticalLayout implements View {

    public static final String                NAME                         = "reports";
    private final HorizontalLayout            toolbar                      = new HorizontalLayout();
    private final Label                       label                        = new Label("MyDashboard");
    private final Panel                       contentPanel                 = new Panel();
    private final GridLayout                  widgetsLayout                = new GridLayout(2, 1);

    private final InvoiceReport               invoiceReport                = new InvoiceReport();
    private final ReportedHoursByProjectChart thisMonthChart               = new ReportedHoursByProjectChart("This month");
    private final ReportedHoursByProjectChart lastMonthChart               = new ReportedHoursByProjectChart("Last month");
    private final Widget                      reportedHoursByProjectChart3 = new TestChart();
    //
    private final AchievoConnectorWrapper     connector                    = SpringUtils.getBean(AchievoConnectorWrapper.class);


    public ReportsView() {
        addComponent(toolbar);
        addComponent(contentPanel);

        toolbar.addComponent(label);
        contentPanel.setContent(widgetsLayout);
        widgetsLayout.addComponent(invoiceReport);
        // widgetsLayout.addComponent(reportedHoursByProjectChart3);
        widgetsLayout.addComponent(thisMonthChart);
        widgetsLayout.addComponent(lastMonthChart);

        setExpandRatio(contentPanel, 100);
        setSizeFull();
        contentPanel.setSizeFull();
        widgetsLayout.setWidth("100%");

        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.addStyleName("toolbar");
        label.addStyleName("h1");

        addStyleName("dashboard-view");
        widgetsLayout.setSpacing(true);
        widgetsLayout.setMargin(new MarginInfo(false, true, true, true));

    }


    @Override
    public void enter(ViewChangeEvent event) {

        Calendar c = Calendar.getInstance();
        c.setTime(DateUtils.truncate(new Date(), Calendar.MONTH));
        Date thisMonthStart = c.getTime();
        c.add(Calendar.MONTH, -1);
        Date lastMonthStart = c.getTime();

        List<WorkReport> reports = connector.getHours(lastMonthStart, new Date());

        List<WorkReport> lastMonthReports = Lists.newLinkedList();
        List<WorkReport> thisMonthReports = Lists.newLinkedList();

        for (WorkReport r : reports) {
            if (r.getDate().before(thisMonthStart)) {
                lastMonthReports.add(r);
            } else {
                thisMonthReports.add(r);
            }
        }

        thisMonthChart.refresh(lastMonthReports, lastMonthStart);
        lastMonthChart.refresh(thisMonthReports, thisMonthStart);
    }
}
