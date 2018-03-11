package com.github.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("t")
public class TestController {


	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() {

		return "success";
	}

}
