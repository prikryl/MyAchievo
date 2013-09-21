package cz.admin24.myachievo.web2.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.google.common.collect.Lists;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import cz.admin24.myachievo.connector.http.dto.WorkReport;
import cz.admin24.myachievo.web2.SpringUtils;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;
import cz.admin24.myachievo.web2.widgets.chart.ReportedHoursByProjectChart;
import cz.admin24.myachievo.web2.widgets.chart.Widget;

@Component
@Scope("prototype")
@VaadinView(ReportsView.NAME)
public class ReportsView extends VerticalLayout implements View {

    public static final String                NAME                         = "reports";

    private final ReportedHoursByProjectChart reportedHoursByProjectChart  = new ReportedHoursByProjectChart();
    private final ReportedHoursByProjectChart reportedHoursByProjectChart2 = new ReportedHoursByProjectChart();
    private final Widget                      reportedHoursByProjectChart3 = new TestChart();
    //
    private final AchievoConnectorWrapper     connector                    = SpringUtils.getBean(AchievoConnectorWrapper.class);


    public ReportsView() {
//        setSizeFull();
    }


    @Override
    public void enter(ViewChangeEvent event) {
         addComponent(reportedHoursByProjectChart3);
        addComponent(reportedHoursByProjectChart);
        addComponent(reportedHoursByProjectChart2);

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

        reportedHoursByProjectChart.refresh(lastMonthReports, lastMonthStart);
        reportedHoursByProjectChart2.refresh(thisMonthReports, thisMonthStart);
    }
}
