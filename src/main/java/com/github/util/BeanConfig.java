package com.github.util;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/** Bean配置管理 */
@Configuration
public class BeanConfig {

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	}
	/* 支持fastjson */
	@Bean
	@ConditionalOnClass({FastJsonHttpMessageConverter.class})
	public HttpMessageConverters fastJsonHttpMessageConverters(StringHttpMessageConverter stringHttpMessageConverter) {

		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullBooleanAsFalse,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.PrettyFormat
		);

		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
			this.add(MediaType.APPLICATION_JSON);
			this.add(MediaType.APPLICATION_JSON_UTF8);
		}});

		return new HttpMessageConverters(fastJsonHttpMessageConverter, stringHttpMessageConverter);
	}

	@Bean
	public EmbeddedServletContainerCustomizer cookieProcessorCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				if (container instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
					factory.addContextCustomizers(new TomcatContextCustomizer() {
						@Override
						public void customize(Context context) {
							context.setCookieProcessor(new LegacyCookieProcessor());
						}
					});
				}
			}
		};
	}

}

