package cz.admin24.myachievo.web2.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.admin24.myachievo.web2.SpringUtils;
import cz.admin24.myachievo.web2.service.AchievoConnectorWrapper;
import cz.admin24.myachievo.web2.widgets.chart.Widget;

public class InvoiceReport extends Widget {
    private final FastDateFormat       periodFormat         = FastDateFormat.getInstance("MMMM yyyy", UI.getCurrent().getLocale());
    private final VerticalLayout       layout               = new VerticalLayout();
    private final FormLayout           formLayout           = new FormLayout();
    private final ComboBox             periodCmb            = new ComboBox();
    private final CheckBox             groupByProjectCb     = new CheckBox("By Project", true);
    private final CheckBox             groupByPhaseCb       = new CheckBox("By Phase", false);
    private final CheckBox             groupByActivityCb    = new CheckBox("By Activity", false);

    private final Button               exportBtn            = new Button("Export");
    // services
    private final InvoiceReportBuilder invoiceReportBuilder = new InvoiceReportBuilder();


    public InvoiceReport() {
        super();

        buildlayout();
        configureForm();

        css();
        localize();
    }


    private void localize() {
        setCaption("Invoice report");
    }


    private void css() {
        layout.setComponentAlignment(exportBtn, Alignment.MIDDLE_RIGHT);
        layout.setExpandRatio(formLayout, 100);

        layout.setMargin(true);

    }


    private void configureForm() {
        Calendar c = Calendar.getInstance(UI.getCurrent().getLocale());
        Date month;
        periodCmb.removeAllItems();

        c.add(Calendar.MONTH, 1);
        month = c.getTime();
        periodCmb.addItem(month);
        periodCmb.setItemCaption(month, periodFormat.format(month) + " (next month)");

        c.add(Calendar.MONTH, -1);
        month = c.getTime();
        periodCmb.addItem(month);
        periodCmb.setItemCaption(month, periodFormat.format(month) + " (this month)");
        periodCmb.setValue(month);

        c.add(Calendar.MONTH, -1);
        month = c.getTime();
        periodCmb.addItem(month);
        periodCmb.setItemCaption(month, periodFormat.format(month) + " (previous month)");

        for (int i = 0; i < 5; i++) {
            c.add(Calendar.MONTH, -1);
            month = c.getTime();
            periodCmb.addItem(month);
            periodCmb.setItemCaption(month, periodFormat.format(month));
        }

        periodCmb.setNullSelectionAllowed(false);

        FileDownloader fileDownloader = new FileDownloader(new FileResource(new File(""))) {
            @Override
            public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) throws IOException {
                String invoiceCsv = invoiceReportBuilder.buildCsv((Date) periodCmb.getValue(), groupByProjectCb.getValue(), groupByPhaseCb.getValue(), groupByActivityCb.getValue(),
                        SpringUtils.getBean(AchievoConnectorWrapper.class));

                setFileDownloadResource(new CsvDownloadResource(invoiceCsv, "invoice " + periodFormat.format((Date) periodCmb.getValue()) + ".csv"));

                return super.handleConnectorRequest(request, response, path);
            }
        };

        fileDownloader.extend(exportBtn);
    }


    private void buildlayout() {
        addComponent(layout);
        layout.addComponent(formLayout);
        layout.addComponent(exportBtn);
        formLayout.addComponent(periodCmb);
        formLayout.addComponent(groupByProjectCb);
        formLayout.addComponent(groupByPhaseCb);
        formLayout.addComponent(groupByActivityCb);

    }

    public static class CsvDownloadResource extends StreamResource {
        private static final long serialVersionUID = 1L;


        public CsvDownloadResource(final String data, String filename) {
            super(new StreamSource() {

                @Override
                public InputStream getStream() {
                    return new StringBufferInputStream(data);
                }
            }, filename);
        }

    }
}
