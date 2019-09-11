package com.yonyou.aco.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.yonyou.aco.ding.DingService;
import com.yonyou.aco.user.service.IUserService;

@Component
@Configuration
@EnableScheduling
public class ScheduleSyncUser {
	private final static Logger logger = LoggerFactory.getLogger(ScheduleSyncUser.class);

	@Autowired
	ExecutorPool executorPool;

	@Autowired
	IUserService userService;

	// 添加定时任务每天23点执行一次：0 0 23 * * ?
	@Scheduled(cron = "0 0 23 * * ?")
	private void configureTasks() {
		logger.info("-----------------开始同步部门及用户信息-----------------");
		List<Department> department = DingService.findAllDepts().stream().filter(obj -> obj.getId() == 127841811).collect(Collectors.toList());

		if (department.size() == 1) {
			List<Userlist> ddUserList = DingService.findUsersByDeptId(department.get(0).getId());
			ddUserList.forEach(dduser -> logger.info("[当前用户名：]" + dduser.getName() + "[手机号：]" + dduser.getMobile() + "[钉钉userIdId]" + dduser.getUserid()));

			// 开始同步钉钉用户
			for (Userlist dduser : ddUserList) {
				boolean result = userService.updateUser(dduser.getMobile(), dduser.getUserid());
				if (result) {
					logger.info("[同步成功：]"  + dduser.getName() + "[手机号为：]" + dduser.getMobile() + "[UserId:]" + dduser.getUserid());
				} else {
					logger.info("[同步失败：]"  + dduser.getName() + "[手机号为：]" + dduser.getMobile() + "[UserId:]" + dduser.getUserid());
				}
			}
		}

		logger.info("-----------------结束同步部门及用户信息-----------------");
	}
}