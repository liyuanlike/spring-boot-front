package com.github.util;

import com.github.service.CsrfTokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class CsrfTokenInterceptor implements HandlerInterceptor {

	@Resource private CsrfTokenService csrfTokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			CsrfToken annotation = method.getAnnotation(CsrfToken.class);

			if (annotation != null) {

				String sessionId = request.getRequestedSessionId();
				boolean remove = annotation.remove();
				if (remove) {
					String clientToken = request.getParameter(CsrfToken.NAME);
					boolean check = csrfTokenService.check(sessionId, clientToken);
					if (!check) {
						response.sendRedirect("/unauthorize");
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (handler instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			CsrfToken annotation = method.getAnnotation(CsrfToken.class);

			if (annotation != null) {

				String sessionId = request.getRequestedSessionId();
				boolean create = annotation.create();
				if (create) {
					String csrfToken = csrfTokenService.create(sessionId);
					modelAndView.addObject(CsrfToken.NAME, csrfToken);
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}

