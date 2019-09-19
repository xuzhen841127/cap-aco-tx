package com.yonyou.aco.realpay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yonyou.aco.realpay.entity.RealpayEntity;
import com.yonyou.aco.realpay.mapper.RealpayMapper;
import com.yonyou.aco.realpay.service.IRealpayService;

/**
 * <p>概 述：拨款申请Service实现类
 * <p>功 能：拨款申请Service实现类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Service
public class RealpayServiceImpl extends ServiceImpl<RealpayMapper, RealpayEntity> implements IRealpayService {
	/**
	 * 查询拨款计划总数
	 * 
	 * @param mobile 手机号
	 * 
	 * @return Integer
	 */
	@Override
	public Integer findRealpayTotal(String mobile) {
		RealpayEntity realpayEntity = new RealpayEntity();
		realpayEntity.setUserCode(mobile);
		return this.baseMapper.findRealpayTotal(realpayEntity);
	}

	/**
	 * 查询个人拨款计划
	 * 
	 * @param mobile	手机号
	 * @param title		查询标
	 * @param pageIndex 页码
	 * @param pageSize  页值
	 * 
	 * @return List<RealpayEntity>
	 */
	@Override
	public List<RealpayEntity> findRealpays(String mobile, String title, Integer pageIndex, Integer pageSize) {
		IPage<RealpayEntity> page = new Page<RealpayEntity>(pageIndex, pageSize);
		QueryWrapper<RealpayEntity> queryWrapper = new QueryWrapper<RealpayEntity>();
		queryWrapper.eq("status", "1");
		queryWrapper.eq("user_code", mobile);
		queryWrapper.and(wrapper -> wrapper.like("menu_name", title).or().like("remark", title).or().like("agency_code", title));
		this.baseMapper.selectPage(page, queryWrapper);
		List<RealpayEntity> realpayList = page.getRecords();
		return realpayList;
	}

	/**
	 * 查询个人拨款计划为发送钉钉待办提醒的记录
	 * 
	 * @param mobile 手机号
	 * @return List<RealpayEntity>
	 */
	public List<RealpayEntity> findRealpayUnNotice() {
		 QueryWrapper<RealpayEntity> queryWrapper = new QueryWrapper<RealpayEntity>();
		 queryWrapper.eq("status", "1"); queryWrapper.eq("is_send_notice", "0");
		 return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 修改拨款计划已发送待办提醒
	 */
	public void updateRealpayNotice(String isSendNotice, String realpayId) {
		this.baseMapper.updateRealpayNotice(isSendNotice, realpayId);
	}
}