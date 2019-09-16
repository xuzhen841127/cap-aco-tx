package com.yonyou.aco.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Scope
public class ExecutorPool {
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);

	public ExecutorService getExecutorService() {
		return executorService;
	}
}