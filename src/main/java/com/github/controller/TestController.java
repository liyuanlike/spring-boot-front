package com.github.controller;

import com.github.model.Photo;
import com.github.pagehelper.PageHelper;
import com.github.service.PhotoService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executor;


@Controller
@RequestMapping("t")
public class TestController {

	@Resource private PhotoService photoService;
	@Resource private TaskExecutor taskExecutor;
	@Resource private BeanFactory beanFactory;
//	@Resource private TaskScheduler taskScheduler;


	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() {

		try {
			System.err.println(beanFactory.getBean(TaskExecutor.class));
		}
		catch (NoUniqueBeanDefinitionException ex) {
			System.err.println(beanFactory.getBean("taskExecutor", Executor.class));
		}
		System.err.println(taskExecutor);



		if (true) {
//			throw new RuntimeException();
		}

		for (int i = 0; i < 100; i++) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					System.err.println("execute...");
				}
			});
		}

		System.err.println(taskExecutor);
		photoService.async();

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
