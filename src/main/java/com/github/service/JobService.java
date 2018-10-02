package com.github.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobService {

	@Scheduled(cron = "*/1 * * * * ? ")   //每4秒执行一次
	public void testCr2on4() {
		System.err.println(Thread.currentThread().toString() + " : " + new Date());
	}

	@Scheduled(cron = "*/1 * * * * ? ")   //每4秒执行一次
	public void testCr3o1n4() {
		System.err.println(Thread.currentThread().toString() + " : " + new Date());
	}
}
