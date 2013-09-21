package cz.admin24.myachievo.web2.security;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;

import cz.admin24.myachievo.connector.http.AchievoConnector;
import cz.admin24.myachievo.connector.http.AchievoConnectorImpl;
import cz.admin24.myachievo.connector.http.AuthentizationException;

public class AchievoAuthenticationProvider implements AuthenticationProvider {
    private static final Logger         LOG      = LoggerFactory.getLogger(AchievoAuthenticationProvider.class);
    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.
     * springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !supports(authentication.getClass())) {
            LOG.debug("Authentication failed: Only UsernamePasswordAuthenticationToken is supported, Used: {}", authentication);
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if (token.getCredentials() == null) {
            LOG.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedUsername = (String) token.getPrincipal();
        String presentedPassword = token.getCredentials().toString();

        AchievoConnector achievoConnector = new AchievoConnectorImpl();
        try {
            achievoConnector.authentize(presentedUsername, presentedPassword);
            return new AchievoAuthenticationToken(presentedUsername, presentedPassword);
        } catch (AuthentizationException e) {
            LOG.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), e);
        } catch (IOException e) {
            LOG.debug("Authentication failed: IOError occured during communication with authentication service.");
            throw new AuthenticationServiceException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Communication error"), e);
        }

    }


    /**
     * We support only UsernamePasswordAuthenticationToken (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        if (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)) {
            return true;
        }
        return false;
    }

}
