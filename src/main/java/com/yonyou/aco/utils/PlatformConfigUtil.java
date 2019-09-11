package com.yonyou.aco.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * <p>概述：配置文件工具类
 * <p>功能：获取配置文件配置信息
 * <p>作者：徐真
 * <p>创建时间：2016-07-24
 * <p>类调用特殊情况：无
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class PlatformConfigUtil {
	private static final Log log = LogFactory.getLog(PlatformConfigUtil.class);

	private static Properties properties;
	private static boolean hasLoad = false;

	/**
	 * 加载属性配置文件
	 * 
	 * @param 无
	 * @return properties 返回属性文件对象
	 */
	private static Properties loadProperties() {
		properties = loadProperties("platformconfig.properties");
		hasLoad = true;
		return properties;
	}

	/**
	 * 根据KEY获取属性值
	 * 
	 * @param key 属性KEY
	 * @return String 返回属性VALUE
	 */
	public static String getString(String key) {
		if (!hasLoad) {
			loadProperties();
		}
		return properties.getProperty(key);
	}

	/**
	 * 返回同一KEY值的所有VALUE
	 * 
	 * @param key 属性KEY
	 * @return List<String> 返回当前VALUE
	 */
	public static List<String> getList(String key) {
		if (!hasLoad) {
			loadProperties();
		}
		List list = new ArrayList();
		if ((key == null) || (key.equals(""))) {
			return list;
		}
		Enumeration propertyNames = properties.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			if ((propertyName != null) && (propertyName.indexOf(key) >= 0)) {
				list.add(properties.getProperty(propertyName));
			}
		}

		return list;
	}

	/**
	 * 加载属性配置文件
	 * 
	 * @param configFileName 属性配置文件路径文件名
	 * @return properties 返回属性文件对象
	 */
	public static Properties loadProperties(String configFileName) {
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadAllProperties(configFileName);
		} catch (Exception e) {
			log.error("文件未找到");
		}

		return props;
	}
}