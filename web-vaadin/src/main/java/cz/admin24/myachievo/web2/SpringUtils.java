package cz.admin24.myachievo.web2;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware {
    private static final Logger       LOG       = LoggerFactory.getLogger(SpringUtils.class);
    // I think proxy should be used if object will be serialized
    private static final Boolean      USE_PROXY = true;

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static <T> T getBean(final Class<T> clz) {
        if (USE_PROXY) {
            return getBeanProxy(clz);
        } else {
            return getBeanNative(clz);
        }
    }


    /**
     * Retuns bean, which is not wrapped by jassist proxy
     *
     * @param clz
     * @return
     */
    public static <T> T getBeanNative(final Class<T> clz) {
        return applicationContext.getBean(clz);
    }


    public static <T> T getBeanProxy(final Class<T> clz) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clz);

        MethodHandler handler = new MethodHandler() {

            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                T bean = applicationContext.getBean(clz);
                return thisMethod.invoke(bean, args);
            }

        };

        try {
            return (T) factory.create(new Class[] {}, new Object[] {}, handler);
        } catch (Exception e) {
            LOG.error("Failed to create object proxy for class {}", clz, e);
            throw new IllegalStateException("Failed to create object proxy for " + clz, e);
        }

    }
}
