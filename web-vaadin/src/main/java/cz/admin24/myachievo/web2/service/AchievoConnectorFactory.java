package cz.admin24.myachievo.web2.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;
import cz.admin24.myachievo.web2.security.AchievoAuthenticationToken;

//@Service("achievoConnectorFactory")
public class AchievoConnectorFactory implements FactoryBean<AchievoConnectorWrapper> {

    @Override
    public AchievoConnectorWrapper getObject() throws Exception {
        AchievoConnector connector = new AchievoConnectorImpl();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new AchievoConnectorWrapper(connector);
        }
        AchievoAuthenticationToken token = (AchievoAuthenticationToken) authentication;

        connector.setCredentials(token.getUsername(), token.getPassword());
        return new AchievoConnectorWrapper(connector);
    }


    @Override
    public Class<?> getObjectType() {
        return AchievoConnectorWrapper.class;
    }


    @Override
    public boolean isSingleton() {
        return false;
    }

}
