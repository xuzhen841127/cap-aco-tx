package com.yonyou.aco.plan.mapper;

import com.yonyou.aco.plan.entity.PlanEntity;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>概 述：用款计划Mapper接口类
 * <p>功 能：用款计划Mapper接口类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Mapper
public interface PlanMapper extends BaseMapper<PlanEntity> {
	public Integer findPlanTotal(PlanEntity planEntity);
}