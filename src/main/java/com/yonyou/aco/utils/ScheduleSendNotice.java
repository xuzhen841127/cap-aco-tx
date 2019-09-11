package com.yonyou.aco.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.yonyou.aco.common.BaseController;
import com.yonyou.aco.ding.DingService;
import com.yonyou.aco.plan.entity.PlanEntity;
import com.yonyou.aco.realpay.entity.RealpayEntity;
import com.yonyou.aco.user.entity.UserEntity;

@Component
@Configuration
@EnableScheduling
public class ScheduleSendNotice extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(ScheduleSendNotice.class);

	// private RedisUtil redisUtil = new RedisUtil();

	private List<UserEntity> userList = null;

	// 添加定时任务, 每分钟刷新一次待办
	@Scheduled(cron = "0 */1 * * * ?")
	private void configureTasks() {
		// 拨款计划发送待办通知
		realpaySendNotice();

		// 用款计划发送待办通知
		planSendNotice();
	}

	/**
	 * 拨款计划发送待办通知
	 */
	public void realpaySendNotice() {
		String realpayId = "";
		String mobile = "";
		String title = "";
		String text = "";
		String url = "";

		List<RealpayEntity> realpayList = realpayService.findRealpayUnNotice();
		for (RealpayEntity realpayEntity : realpayList) {
			// 发送拨款计划待办提醒
			String ddUserId = getUserDDId(realpayEntity.getUserCode());
			if (ddUserId == null) {
				continue;
			}
			realpayId = realpayEntity.getRealpayId();
			mobile = realpayEntity.getUserCode();
			title = realpayEntity.getMenuName();
			text = realpayEntity.getRemark();
			url = PlatformConfigUtil.getString("APP_URL") + "/realpayController/toRealpayHomeView?mobile=" + mobile + "&realpayId=" + realpayId;
			System.out.println(url);
			OapiMessageCorpconversationAsyncsendV2Response response = DingService.sendNotice(ddUserId, title, text, url);

			// 更新当前拨款计划已发送待办通知
			if (response.getErrorCode().equals("0")) {
				realpayService.updateRealpayNotice("1", realpayEntity.getRealpayId());
				logger.info("已发送待办通知给-" + realpayEntity.getUserName() + "-" + title);
			} else {
				logger.info("发送通知失败-" + realpayEntity.getUserName() + "-" + title + "错误消息：" + response.getErrmsg());
			}
		}
	}

	/**
	 * 用款计划发送待办通知
	 */
	public void planSendNotice() {
		String planId = "";
		String mobile = "";
		String title = "";
		String text = "";
		String url = "";

		List<PlanEntity> planList = planService.findPlanUnNotice();
		for (PlanEntity planEntity : planList) {
			// 发送拨款计划待办提醒
			String ddUserId = getUserDDId(planEntity.getUserCode());
			if (ddUserId == null) {
				continue;
			}
			planId = planEntity.getPlanId();
			mobile = planEntity.getUserCode();
			title = planEntity.getMenuName();
			text = planEntity.getRemark();
			url = PlatformConfigUtil.getString("APP_URL") + "/realpayController/toPlanHomeView?mobile=" + mobile + "&planId=" + planId;
			System.out.println(url);
			OapiMessageCorpconversationAsyncsendV2Response response = DingService.sendNotice(ddUserId, title, text, url);

			// 更新当前用款计划已发送待办通知
			if (response.getErrorCode().equals("0")) {
				planService.updatePlanNotice("1", planId);
				logger.info("已发送待办通知给-" + planEntity.getUserName() + "-" + title);
			} else {
				logger.info("发送通知失败-" + planEntity.getUserName() + "-" + title + "错误消息：" + response.getErrmsg());
			}
		}
	}

	/**
	 * 通过UserCode获取用户ddUserId
	 * 
	 * @param userCode
	 * @return
	 */
	public String getUserDDId(String userCode) {
		if (userList == null) {
			userList = userService.findAllUser();
		}

		List<UserEntity> resultList = userList.stream().filter(obj -> obj.getTelephone().equals(userCode)).collect(Collectors.toList());
		if (resultList.size() == 1) {
			return resultList.get(0).getDdUserId();
		}

		return null;

		/*List<UserEntity> userList = null;
		if (redisUtil.get("userList") == null) {
			userList = userService.findAllUser();
			redisUtil.lSet("userList", userList);
		} else {
			List<UserEntity> resultList = userList.stream().filter(obj -> obj.getTelephone().equals(userCode)).collect(Collectors.toList());
			if (resultList.size() == 1) {
				return resultList.get(0).getDdUserId();
			}
		}

		return null;*/
	}
}