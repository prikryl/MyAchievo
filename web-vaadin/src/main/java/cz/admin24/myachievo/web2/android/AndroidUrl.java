package cz.admin24.myachievo.web2.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidUrl {

    private static final Logger LOG = LoggerFactory.getLogger(AndroidUrl.class);


    public String toFragment() {
        return AndroidView.NAME;
    }


    public String toUrl() {
        return "#!" + toFragment();
    }

}
