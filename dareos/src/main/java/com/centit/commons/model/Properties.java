package com.centit.commons.model;

import java.io.InputStream;

import com.centit.commons.util.StringUtil;

/**
 * 
 * @ClassName: Properties 
 * @Description: 读取配置文件
 * @author: liu_yg
 * @date: 2019年2月26日 下午4:04:48
 */
public class Properties {

	private static java.util.Properties properties=new java.util.Properties();  
    static{  
        ClassLoader classLoader=Properties.class.getClassLoader();  
        InputStream ips=classLoader.getResourceAsStream("/properties/conf.properties");  
        try{  
            properties.load(ips);  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static String getString(String key){  
        return properties.getProperty(key);  
    }  
    
    public static boolean getBoolean(String key) {
		return "true".equals(getString(key));
	}
    
    public static int getInt(String key) {
		String value = getString(key);
		if (!StringUtil.isEmpty(value)) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * Gets the str list.
	 * 
	 * @param key the key
	 * 
	 * @return the str list
	 */
	public static String getStrList(String key) {
		String value = getString(key);
		if (!StringUtil.isEmpty(value)) {
			try {
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "8";
	}

}