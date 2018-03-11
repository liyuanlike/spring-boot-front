package com.github.controller;

import com.github.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class IndexController {

	@Resource private UserService userService;

	@GetMapping({"", "/"})
	public String login() {
		return "login";
	}

	@PostMapping({"", "/"})
	public String login(Model model) {
		return "login";
	}


	@GetMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		session.invalidate();

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setValue(null);
			cookie.setDomain("chaoxing.com");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/login";
	}

	@GetMapping("index")
	public String index() {
		return "index";
	}

}
