package cz.admin24.myachievo.web2.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportsUrl {

    private static final Logger LOG = LoggerFactory.getLogger(ReportsUrl.class);


    public String toFragment() {
        return ReportsView.NAME;
    }


    public String toUrl() {
        return "#!" + toFragment();
    }

}
