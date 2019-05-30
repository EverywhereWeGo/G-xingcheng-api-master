package com.bfd.api.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigUtils {
	
    public static Properties props;
    
    static{
    	if(props==null){
    		 try {
				props=PropertiesLoaderUtils.loadAllProperties("application.properties");
			} catch (IOException e) {
				props=null;
				e.printStackTrace();
			}
    	}
    }
    
    public static String getString(String key){
    	return props.getProperty(key);
    }
    
}
