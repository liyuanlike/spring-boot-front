package com.github.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class DynamicBean {
	
	private Object object = null;
	private BeanMap beanMap = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicBean.class);

	public DynamicBean(Map<String, Class<?>> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	public DynamicBean(Class<?> clazz) {
		this(getBeanPropertyMap(clazz));
	}
	public DynamicBean() {
		super();
	}

	public BeanMap getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(BeanMap beanMap) {
		this.beanMap = beanMap;
	}

	public Object getObject() {
		return this.object;
	}

	public static Object generateBean(Map<String, Class<?>> propertyMap) {
		BeanGenerator beanGenerator = new BeanGenerator();
		BeanGenerator.addProperties(beanGenerator, propertyMap);
		/*
		for (Map.Entry<String, Class> entry : propertyMap.entrySet()) {
			// 给bean添加属性;即属性对应的类型;
			beanGenerator.addProperty(entry.getKey(), entry.getValue());
		}*/
		return beanGenerator.create();
	}

	public void setPropertyByName(String propertyName, Object value) {
		this.beanMap.put(propertyName, value);
	}

	public Object getPropertyByName(String propertyName) {
		return this.beanMap.get(propertyName);
	}

	public static Map<String, Class<?>> getBeanPropertyMap(Class<?> clazz) {
		Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers())) {
				propertyMap.put(field.getName(), field.getType());
			}
		}
		return propertyMap;
	}
	
	/** objects只能对象, 不能集合*/
	public static Object getProxyBo(Object bean, Object... objects) {
		
		// 创建对象
		Map<String, Class<?>> propertyMap = getBeanPropertyMap(bean.getClass());
		for (Object object : objects) {
			if (object != null) {
				propertyMap.put(StringUtils.uncapitalize(object.getClass().getSimpleName()), object.getClass());
			} else {
				LOGGER.warn("reflect object is null.");
			}
		}
		DynamicBean dynamicBean = new DynamicBean(propertyMap);
		
		// 赋值
		for (Object object : objects) {
			if (object != null) {
				dynamicBean.setPropertyByName(StringUtils.uncapitalize(object.getClass().getSimpleName()), object);
			} 
		}
		Object object = dynamicBean.getObject();
		BeanUtils.copyProperties(bean, object);
		
		return object;
	}

	/** 向bean中添加一个属性并赋值 */
	public static Object addProperty(Object bean, String propertyName, Object value) {

		// 创建对象
		Map<String, Class<?>> propertyMap = getBeanPropertyMap(bean.getClass());
		propertyMap.put(propertyName, value.getClass());
		DynamicBean dynamicBean = new DynamicBean(propertyMap);

		// 赋值
		Object object = dynamicBean.getObject();
		BeanUtils.copyProperties(bean, object);
		dynamicBean.setPropertyByName(propertyName, value);

		return object;
	}
}

