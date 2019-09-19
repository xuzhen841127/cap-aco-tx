package com.yonyou.aco.user.service;

import com.yonyou.aco.user.entity.UserEntity;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>概 述：用户Dao接口实现类
 * <p>功 能：用户Dao接口实现类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
public interface IUserService extends IService<UserEntity> {
	public List<UserEntity> findAllUser();

	public boolean updateUser(String telephone, String ddUserId);
}