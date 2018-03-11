package com.github.controller;

import com.github.model.Photo;
import com.github.service.PhotoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("t")
public class TestController {

	@Resource private PhotoService photoService;


	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() {

		Photo photo = new Photo();
		photo.setName("Baby");
		photoService.add(photo);

		photoService.get(photo.getId());
		photoService.get(photo.getId());

		photo.setName("iMiracle");
		photoService.update(photo);
		photoService.get(photo.getId());

		photoService.delete(photo.getId());
		photoService.get(photo.getId());

		return "success";
	}

}
