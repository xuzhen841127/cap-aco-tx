package com.yonyou.aco.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.realpay.service.IRealpayService;
import com.yonyou.aco.user.service.IUserService;
import com.yonyou.aco.utils.ExecutorPool;
import com.yonyou.aco.utils.RedisUtil;

/**
 * <p> 概述：Controller基类，所有controller继承
 * <p> 功能：统一管理Controller中，需要Spring依赖注入的bean对象
 * <p> 作者：徐真
 * <p> 创建时间：2019年9月7日
 * <p> 类调用特殊情况：无
 */
public class BaseController {
	@Autowired
	public RedisUtil redisUtil;

	@Autowired
	public ExecutorPool executorPool;

	@Autowired
	public IPlanService planService;

	@Autowired
	public IRealpayService realpayService;

	@Autowired
	public IUserService userService;
}