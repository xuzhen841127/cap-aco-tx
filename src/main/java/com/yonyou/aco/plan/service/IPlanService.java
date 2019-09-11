package com.yonyou.aco.plan.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yonyou.aco.plan.entity.PlanEntity;

/**
 * <p>概 述：用款计划Server接口类
 * <p>功 能：用款计划Server接口类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
public interface IPlanService extends IService<PlanEntity> {
	public Integer findPlanTotal(String mobile);

	public List<PlanEntity> findPlans(String mobile, String title, Integer pageIndex, Integer pageSize);

	public List<PlanEntity> findPlanUnNotice();

	public void updatePlanNotice(String isSendNotice, String planId);
}