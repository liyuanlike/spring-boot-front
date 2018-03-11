package com.github.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Resource private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver);
		super.addArgumentResolvers(argumentResolvers);
	}
}

