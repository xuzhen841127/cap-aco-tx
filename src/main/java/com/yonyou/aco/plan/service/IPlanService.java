package com.yonyou.aco.plan.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yonyou.aco.plan.entity.PlanEntity;

/**
 * <p> 服务类  </p>
 *
 * @author 徐真
 * @since 2019-08-29
 */
public interface IPlanService extends IService<PlanEntity> {
	public Integer findPlanTotal(String mobile);
	
	public List<PlanEntity> findPlans(String mobile, String title, Integer pageIndex, Integer pageSize);
}