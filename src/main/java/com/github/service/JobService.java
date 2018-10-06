package com.github.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class JobService {

	private static AtomicInteger count = new AtomicInteger(0);

	@Scheduled(cron = "0/1 * * * * ? ")
	public void t1() {
		System.err.println(Thread.currentThread().toString() + " : " + count.incrementAndGet()  + " : " + new Date());
	}

	@Scheduled(cron = "0/1 * * * * ? ")
	public void t2() {
		System.err.println(Thread.currentThread().toString() + " : " + count.incrementAndGet() + " : " + new Date());
	}

	@Scheduled(cron = "0/1 * * * * ? ")
	public void t3() {
		System.err.println(Thread.currentThread().toString() + " : " + count.incrementAndGet() + " : " + new Date());
	}

	@Scheduled(cron = "0/1 * * * * ? ")
	public void t4() {
		System.err.println(Thread.currentThread().toString() + " : " + count.incrementAndGet() + " : " + new Date());
	}

	@Scheduled(cron = "0/1 * * * * ? ")
	public void t5() {
		System.err.println(Thread.currentThread().toString() + " : " + count.incrementAndGet() + " : " + new Date());
	}
}
