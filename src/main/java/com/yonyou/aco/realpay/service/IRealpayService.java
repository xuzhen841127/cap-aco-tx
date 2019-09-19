package com.yonyou.aco.realpay.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yonyou.aco.realpay.entity.RealpayEntity;

/**
 * <p>概 述：拨款申请Server接口类
 * <p>功 能：拨款申请Server接口类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
public interface IRealpayService extends IService<RealpayEntity> {
	public Integer findRealpayTotal(String mobile);

	public List<RealpayEntity> findRealpays(String mobile, String title, Integer pageIndex, Integer pageSize);

	public List<RealpayEntity> findRealpayUnNotice();

	public void updateRealpayNotice(String isSendNotice, String realpayId);
}