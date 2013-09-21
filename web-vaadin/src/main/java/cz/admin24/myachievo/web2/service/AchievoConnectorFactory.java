package cz.admin24.myachievo.web2.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;
import cz.admin24.myachievo.web2.security.AchievoAuthenticationToken;

//@Service("achievoConnectorFactory")
public class AchievoConnectorFactory implements FactoryBean<AchievoConnectorWrapper>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private WorkReportCache    workReportCache;


    @Override
    public AchievoConnectorWrapper getObject() throws Exception {
        AchievoConnector connector = new AchievoConnectorImpl();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            AchievoAuthenticationToken token = (AchievoAuthenticationToken) authentication;
            connector.setCredentials(token.getUsername(), token.getPassword());
        }

        AchievoConnectorWrapper achievoConnectorWrapper = new AchievoConnectorWrapper(connector, workReportCache);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(achievoConnectorWrapper);

        return achievoConnectorWrapper;
    }


    @Override
    public Class<?> getObjectType() {
        return AchievoConnectorWrapper.class;
    }


    @Override
    public boolean isSingleton() {
        return false;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
