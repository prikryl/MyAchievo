package cz.admin24.myachievo.web.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;
import cz.admin24.myachievo.web.dao.AchievoAccountDAO;
import cz.admin24.myachievo.web.entity.AchievoAccount;
import cz.admin24.myachievo.web.security.SocialUserWithId;

//@Service("achievoConnectorFactory")
public class AchievoConnectorFactory implements FactoryBean<AchievoConnector> {
    @Autowired
    private AchievoAccountDAO achievoAccountDAO;
    @Autowired
    private TextEncryptor     textEncryptor;


    @Override
    public AchievoConnector getObject() throws Exception {
        AchievoConnector ret = new AchievoConnectorImpl();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ret;
        }
        SocialUserWithId actualUser = (SocialUserWithId) authentication.getPrincipal();
        AchievoAccount account = achievoAccountDAO.findById(actualUser.getUserUUID());

        ret.setCredentials(account.getUsername(), textEncryptor.decrypt(account.getEncryptedPassword()));
        return ret;
    }


    @Override
    public Class<?> getObjectType() {
        return AchievoConnector.class;
    }


    @Override
    public boolean isSingleton() {
        return false;
    }

}
