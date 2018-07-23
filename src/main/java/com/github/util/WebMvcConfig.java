package com.github.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Resource private CsrfTokenInterceptor csrfTokenInterceptor;
	@Resource private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		/*exceptionResolvers.add(0, new HandlerExceptionResolver() {
			@Resource private FastJsonConfig fastJsonConfig;
			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

				ModelAndView modelAndView = null;
				HandlerMethod handlerMethod = (HandlerMethod) handler;

				Annotation responseBodyAnnotation = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
				Annotation restControllerAnnotation = handlerMethod.getBeanType().getAnnotation(RestController.class);

				if (responseBodyAnnotation != null || restControllerAnnotation != null) {
					FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
					fastJsonJsonView.setFastJsonConfig(fastJsonConfig);

					System.err.println(response.getStatus());
					modelAndView = new ModelAndView(fastJsonJsonView);
					modelAndView.addObject("success");
				}

				return modelAndView;
			}
		});*/
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
		registry.addInterceptor(csrfTokenInterceptor).addPathPatterns("/**");
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


	@Bean
	public WebMvcRegistrations webMvcRegistrations() {
		return new WebMvcRegistrations() {
			@Override
			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
				return new EnhanceRequestMappingHandlerMapping();
			}
		};
	}

	private static final class EnhanceRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
		@Override
		protected boolean isHandler(Class<?> beanType) {
			return super.isHandler(beanType) && beanType.getPackage().getName().contains("controller");
		}
	}
}

