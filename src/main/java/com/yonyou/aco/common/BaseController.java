package com.yonyou.aco.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.aco.plan.service.IPlanService;
import com.yonyou.aco.realpay.service.IRealpayService;

/**
 * <p> ������Controller����
 * <p> ���ܣ�Controller����
 * <p> ���ߣ�����
 * <p> ����ʱ�䣺2019��9��7��
 * <p> ����������������
 */
public class BaseController {
	@Autowired
	public IPlanService planService;

	@Autowired
	public IRealpayService realpayService;	
}