package com.yonyou.aco.plan.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yonyou.aco.plan.entity.PlanEntity;
import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.utils.DateUtil;
import com.yonyou.aco.utils.ExecutorPool;
import com.yonyou.aco.utils.IProtocolUtils;

import net.sf.json.JSONArray;

/**
 * <p>概 述：用款计划Controller类
 * <p>功 能：用款计划Controller类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@RestController
@RequestMapping("/planController")
public class PlanController {
	@Autowired
	IPlanService planService;
	@Autowired
	ExecutorPool executorPool;

	private IProtocolUtils ipu;

	public PlanController() {
		ipu = IProtocolUtils.getInstance();
	}

	/**
	 * 跳转用款计划主页
	 * 
	 * @return ModelAndView 视图跳转
	 */
	@RequestMapping("/toPlanHome")
	public ModelAndView toPlanHome(@RequestParam(value = "mobile", required = true) String mobile) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("mobile", mobile);
		mv.setViewName("/plan/plan-home");
		return mv;
	}

	/**
	 * 获取列表数据
	 * 
	 * @param mobile	手机号
	 * @param pageIndex	页码
	 * @param pageSize	每页条数
	 * @param keyword	搜索关键字
	 * 
	 * @return String json字符串
	 */
	@RequestMapping("findPlans")
	@ResponseBody
	public String findPlans(@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(value = "title", defaultValue = "") String title) {
		String resultStr = "";

		try {
			List<PlanEntity> planList = planService.findPlans(mobile, title, pageIndex, pageSize);
			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(planList).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}

	/**
	 * 跳转到用款计划详情页
	 * 
	 * @param id 主键ID
	 * @return	ModelAndView
	 */
	@RequestMapping("/toPlanDetail")
	public ModelAndView toPlanDetail(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "planId", required = true) String planId) {
		ModelAndView mv = new ModelAndView();
		PlanEntity planEntity = planService.getById(planId);
		mv.addObject("planEntity", planEntity);
		mv.addObject("mobile", mobile);
		mv.setViewName("/plan/plan-detail");
		return mv;
	}

	/**
	 * 跳转到用款计划签批页
	 * 
	 * @param planId 主键ID
	 * @param signType 签批类型
	 * @return
	 */
	@RequestMapping("/toPlanSign")
	public ModelAndView toPlanSign(
			@RequestParam(value = "planId", required = true) String planId,
			@RequestParam(value = "signType", required = true) String signType,
			@RequestParam(value = "mobile", required = true) String mobile) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("planId", planId);
		mv.addObject("signType", signType);
		mv.addObject("mobile", mobile);
		mv.setViewName("/plan/plan-sign");
		return mv;
	}

	/**
	 * 发送用款计划审批
	 * 
	 * @param planId 主键ID
	 * @param suggestion 文字签批意见
	 * @return
	 */
	@RequestMapping("/doSendPlan")
	@ResponseBody
	public String doSendPlan(
			@RequestParam(value = "planId", required = true) String planId,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "suggestion", required = true) String suggestion) {
		String resultStr = "";

		try {
			// 修改用款计划状态为2
			PlanEntity planEntity = planService.getById(planId);
			planEntity.setStatus(Double.valueOf(2));
			planEntity.setOutdate(DateUtil.formatDate(new Date()));
			planEntity.setTaskOpinion(suggestion);
			boolean result = planService.updateById(planEntity);

			// 发送待办提醒
			/*
			 * String finalTitle = planEntity.getMenuName(); String finalBizContent =
			 * planEntity.getMenuName(); String finalUrl =
			 * "http://125.35.5.117:8080/dingController/toDingHome/?mobile=" + mobile;
			 * executorPool.getExecutorService().execute(new Runnable() {
			 * 
			 * @Override public void run() { String userIds = "1024273829788879";
			 * DingService.sendNotice(userIds, finalTitle, finalBizContent, finalUrl); } });
			 */

			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}
}