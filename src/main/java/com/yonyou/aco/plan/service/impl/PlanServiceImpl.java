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
 * <p>概 述：用款计划Service实现类
 * <p>功 能：用款计划Service实现类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, PlanEntity> implements IPlanService {
	/**
	 * 查询用款计划总数
	 * 
	 * @param mobile 手机号
	 * 
	 * @return Integer
	 */
	@Override
	public Integer findPlanTotal(String mobile) {
		PlanEntity planEntity = new PlanEntity();
		planEntity.setUserCode(mobile);
		return this.baseMapper.findPlanTotal(planEntity);
	}

	/**
	 * 查询个人用款计划
	 * 
	 * @param mobile	手机号
	 * @param title		查询标
	 * @param pageIndex 页码
	 * @param pageSize  页值
	 * 
	 * @return List<RealpayEntity>
	 */
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

	/**
	 * 查询个人用款计划为发送钉钉待办提醒的记录
	 * 
	 * @param mobile 手机号
	 * 
	 * @return List<RealpayEntity>
	 */
	@Override
	public List<PlanEntity> findPlanUnNotice() {
		 QueryWrapper<PlanEntity> queryWrapper = new QueryWrapper<PlanEntity>();
		 queryWrapper.eq("status", "1"); queryWrapper.eq("is_send_notice", "0");
		 return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 修改用款计划已发送待办提醒
	 */
	@Override
	public void updatePlanNotice(String isSendNotice, String planId) {
		this.baseMapper.updatePlanNotice(isSendNotice, planId);
	}
}