package com.github.util;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

//@Component
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

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
}
