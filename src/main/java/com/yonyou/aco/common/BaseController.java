package com.yonyou.aco.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.realpay.service.IRealpayService;

/**
 * <p> 概述：Controller基类
 * <p> 功能：Controller基类
 * <p> 作者：徐真
 * <p> 创建时间：2019年9月7日
 * <p> 类调用特殊情况：无
 */
public class BaseController {
	@Autowired
	public IPlanService planService;

	@Autowired
	public IRealpayService realpayService;	
}