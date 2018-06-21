package com.github.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Resource private CsrfTokenInterceptor csrfTokenInterceptor;
	@Resource private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
		super.configurePathMatch(configurer);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver);
		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
		registry.addInterceptor(csrfTokenInterceptor).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				if (StringUtils.isNotEmpty(source)) {
					try {
						return DateUtils.parseDate(source, new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
}

