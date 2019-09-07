package com.yonyou.aco.realpay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yonyou.aco.realpay.entity.RealpayEntity;

/**
 * <p>概 述：拨款计划Dao接口实现类
 * <p>功 能：拨款计划Dao接口实现类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Mapper
public interface RealpayMapper extends BaseMapper<RealpayEntity> {
	public Integer findRealpayTotal(RealpayEntity realpayEntity);

	public List<RealpayEntity> findRealpays(String title, Integer pageIndex, Integer pageSize);
}