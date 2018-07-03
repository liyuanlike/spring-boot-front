package com.github.controller;

import com.github.model.Photo;
import com.github.pagehelper.PageHelper;
import com.github.service.PhotoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


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

	@ResponseBody
	@GetMapping("page")
	public Object page() {

		PageHelper.startPage(1, 2);
		List<Photo> photoList = photoService.getList();

		return photoList;
	}

	@ResponseBody
	@GetMapping("retry")
	public Object retry() {

		photoService.retry();
		return "success";
	}

}
