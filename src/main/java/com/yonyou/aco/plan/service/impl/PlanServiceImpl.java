package com.yonyou.aco.plan.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yonyou.aco.plan.entity.PlanEntity;
import com.yonyou.aco.plan.mapper.PlanMapper;
import com.yonyou.aco.plan.service.IPlanService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 徐真
 * @since 2019-08-29
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, PlanEntity> implements IPlanService {
	@Override
	public Integer findPlanTotal(String mobile) {
		PlanEntity planEntity = new PlanEntity();
		planEntity.setUserCode(mobile);
		return this.baseMapper.findPlanTotal(planEntity);
	}

	@Override
	public List<PlanEntity> findPlans(String mobile, String title, Integer pageIndex, Integer pageSize) {
		IPage<PlanEntity> page = new Page<PlanEntity>(pageIndex, pageSize);
		QueryWrapper<PlanEntity> queryWrapper = new QueryWrapper<PlanEntity>();
		queryWrapper.eq("status", "1");
		queryWrapper.eq("user_code", mobile);
		queryWrapper.and(wrapper -> wrapper.like("menu_name", title).or().like("remark", title));
		this.baseMapper.selectPage(page, queryWrapper);
		List<PlanEntity> planList = page.getRecords();
		return planList;
	}
}