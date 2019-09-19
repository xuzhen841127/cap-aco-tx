package com.yonyou.aco.realpay.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yonyou.aco.realpay.entity.RealpayEntity;
import com.yonyou.aco.realpay.service.IRealpayService;
import com.yonyou.aco.utils.DateUtil;
import com.yonyou.aco.utils.IProtocolUtils;

import net.sf.json.JSONArray;

/**
 * <p>概 述：拨款申请Controller类
 * <p>功 能：拨款申请Controller类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@RestController
@RequestMapping("/realpayController")
public class RealpayController {
	@Autowired
	IRealpayService realpayService;

	private IProtocolUtils ipu;

	public RealpayController() {
		ipu = IProtocolUtils.getInstance();
	}

	/**
	 * 跳转拨款计划主页
	 * 
	 * @return ModelAndView 视图跳转
	 */
	@RequestMapping("/toRealpayHome")
	public ModelAndView toRealpayHome(@RequestParam(value = "mobile", required = true) String mobile) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("mobile", mobile);
		mv.setViewName("/realpay/realpay-home");
		return mv;
	}

	/**
	 * 钉钉待办跳转
	 * 
	 * @param id 主键ID
	 * @return
	 */
	@RequestMapping("/toRealpayHomeView")
	public ModelAndView toRealpayHomeView(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "realpayId", required = true) String realpayId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("mobile", mobile);
		mv.addObject("realpayId", realpayId);
		mv.addObject("viewType", "realpayView");
		mv.setViewName("/ding/ding-home");
		return mv;
	}

	/**
	 * 获取拨款列表数据
	 * 
	 * @param mobile
	 * @param pageIndex
	 * @param pageSize
	 * @param depProName
	 * @return
	 */
	@RequestMapping("findRealpays")
	@ResponseBody
	public String findRealpays(
			@RequestParam(value = "mobile", defaultValue = "") String mobile,
			@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(value = "title", defaultValue = "") String title) {
		String resultStr = "";

		try {
			List<RealpayEntity> realpayList = realpayService.findRealpays(mobile, title, pageIndex, pageSize);

			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(realpayList).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}

	/**
	 * 跳转到拨款计划详情页
	 * 
	 * @param id 主键ID
	 * @return
	 */
	@RequestMapping("/toRealpayDetail")
	public ModelAndView toRealpayDetail(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "realpayId", required = true) String realpayId) {
		ModelAndView mv = new ModelAndView();
		RealpayEntity realpayEntity = realpayService.getById(realpayId);
		mv.addObject("realpayEntity", realpayEntity);
		mv.addObject("mobile", mobile);
		mv.setViewName("/realpay/realpay-detail");
		return mv;
	}

	/**
	 * 跳转到拨款计划签批页
	 * 
	 * @param signType 签批类型
	 * @return ModelAndView
	 */
	@RequestMapping("/toRealpaySign")
	public ModelAndView toPlanSign(
			@RequestParam(value = "realpayId", required = true) String realpayId,
			@RequestParam(value = "signType", required = true) String signType,
			@RequestParam(value = "mobile", required = true) String mobile) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("realpayId", realpayId);
		mv.addObject("signType", signType);
		mv.addObject("mobile", mobile);
		mv.setViewName("/realpay/realpay-sign");
		return mv;
	}

	/**
	 * 发送拨款计划审批
	 * 
	 * @param planId 主键ID
	 * @param suggestion 文字签批意见
	 * @return
	 */
	@RequestMapping("/doSendRealpay")
	@ResponseBody
	public String doSendRealpay(
			@RequestParam(value = "realpayId", required = true) String realpayId,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "suggestion", required = true) String suggestion) {
		String resultStr = "";

		try {
			// 修改拨款计划状态为2
			RealpayEntity realpayEntity = realpayService.getById(realpayId);
			realpayEntity.setStatus(Double.valueOf(2));
			realpayEntity.setOutdate(DateUtil.formatDate(new Date()));
			realpayEntity.setTaskOpinion(suggestion);
			boolean result = realpayService.updateById(realpayEntity);
			
			resultStr = ipu.createHeader(IProtocolUtils.Type.success, JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			resultStr = ipu.createHeader(IProtocolUtils.Type.failed, null);
			e.printStackTrace();
		}

		return resultStr;
	}
}