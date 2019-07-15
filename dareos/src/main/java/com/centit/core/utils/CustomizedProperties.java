package com.centit.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @ClassName: CustomizedProperties.java
 * @Description: 自定义PropertyPlaceholderConfigurer，存储properties文件内容，
 * 		代码中直接使用CustomizedProperties.getContextProperty(key)获取配置文件的内容
 * @author: zhu_hj
 * @date: 2018年5月2日 下午2:56:17
 */
public class CustomizedProperties extends PropertyPlaceholderConfigurer {
    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static String getContextProperty(String name) {
    	Object o=ctxPropertiesMap.get(name);
        return o==null?null:o.toString();

    }
}