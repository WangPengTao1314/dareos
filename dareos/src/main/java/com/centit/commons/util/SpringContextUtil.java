package com.centit.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 
 * @author liu_j
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * @param  applicationContext ApplicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {

        return applicationContext;

    }

    /**
     * 
     * @param name String
     * @return Object 
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {

        return applicationContext.getBean(name);
    }

}
