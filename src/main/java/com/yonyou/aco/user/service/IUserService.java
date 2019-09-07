package com.yonyou.aco.user.service;

import com.yonyou.aco.user.entity.UserEntity;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 徐真
 * @since 2019-09-04
 */
public interface IUserService extends IService<UserEntity> {
	public List<UserEntity> findAllUser();

	public boolean updateUser(String telephone, String ddUserId);
}
