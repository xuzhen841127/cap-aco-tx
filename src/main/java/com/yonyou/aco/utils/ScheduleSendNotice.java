package com.yonyou.aco.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.realpay.entity.RealpayEntity;
import com.yonyou.aco.realpay.service.IRealpayService;

@Component
@Configuration
@EnableScheduling
public class ScheduleSendNotice {
	private final static Logger logger = LoggerFactory.getLogger(ScheduleSendNotice.class);

	@Autowired
	ExecutorPool executorPool;

	@Autowired
	IPlanService planService;

	@Autowired
	IRealpayService realpayService;

	// 3.添加定时任务
	@Scheduled(cron = "0 */1 * * * ?")
	// 或直接指定时间间隔，例如：5秒
	private void configureTasks() {
		List<RealpayEntity> realpayList = realpayService.findRealpayUnNotice();
		//dingServer.sendNotice(userIds, title, text, url);
	}
}