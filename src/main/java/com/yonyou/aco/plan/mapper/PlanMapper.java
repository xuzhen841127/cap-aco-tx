package com.yonyou.aco.plan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yonyou.aco.plan.entity.PlanEntity;

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
	
	public List<PlanEntity> findPlans(String title, Integer pageIndex, Integer pageSize);
	
	public List<PlanEntity> findPlanUnNotice();
	
	public void updatePlanNotice(@Param("isSendNotice") String isSendNotice, @Param("realpayId") String planId);
}