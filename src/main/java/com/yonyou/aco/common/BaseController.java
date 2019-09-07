package com.yonyou.aco.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.realpay.service.IRealpayService;

public class BaseController {
	@Autowired
	public IPlanService planService;

	@Autowired
	public IRealpayService realpayService;	
}