package cz.admin24.myachievo.android;

import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;

public class AchievoConnectorFactory {
    public static final AchievoConnectorImpl ACHIEVO_PROXY = new AchievoConnectorImpl();


    public static AchievoConnectorImpl getInstance() {
        return ACHIEVO_PROXY;
    }

}
