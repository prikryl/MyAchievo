package cz.admin24.myachievo.web2.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardUrl {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardUrl.class);


    public String toFragment() {
        return DashboardView.NAME;
    }


    public String toUrl() {
        return "#!" + toFragment();
    }

}
