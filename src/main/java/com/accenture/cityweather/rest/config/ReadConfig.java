package com.accenture.cityweather.rest.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.accenture.cityweather.rest.util.AppCache;
/** 
 * This class is to read the configuration properties
 */
public class ReadConfig {
	InputStream inputStream;

	public ConcurrentHashMap<String, String> loadPropValue(String key) throws IOException {
		String result = "";
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			result = prop.getProperty(key);
			AppCache.configCache.put(key, result);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return AppCache.configCache;
	}

	public String getProperty(String key) {
		String propertyValue = "";
		if (AppCache.configCache == null) {
			try {
				AppCache.configCache = new ConcurrentHashMap<>();
				AppCache.configCache = loadPropValue(key);
				propertyValue = AppCache.configCache.get(key);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		} else {
			if (AppCache.configCache.containsKey(key)) {
				propertyValue = AppCache.configCache.get(key);
			} else {
				try {
					propertyValue = loadPropValue(key).get(key);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
		return propertyValue;
	}

}
