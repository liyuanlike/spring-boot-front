package com.github.util;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;


/**
 * 从Spring上下文加载的配置文件中取值
 * propertiesUtils.getPropertiesValue("${wechat.pay.notifyUrl}")
 */
@Component
public class PropertiesUtils implements EmbeddedValueResolverAware {
	
	
	private StringValueResolver stringValueResolver;

	@Override
	public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
		this.stringValueResolver = stringValueResolver;
	}	
	public StringValueResolver getStringValueResolver() {
		return stringValueResolver;
	}


	public String getPropertiesValue(String name) {
        return stringValueResolver.resolveStringValue(name);
    }

}
