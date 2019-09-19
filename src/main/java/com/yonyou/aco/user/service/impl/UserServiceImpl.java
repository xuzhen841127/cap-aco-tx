package com.yonyou.aco.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yonyou.aco.user.entity.UserEntity;
import com.yonyou.aco.user.mapper.UserMapper;
import com.yonyou.aco.user.service.IUserService;

/**
 * <p>概 述：用户Dao接口实现类
 * <p>功 能：用户Dao接口实现类
 * <p>作 者：徐真
 * <p>创建时间：2019年8月30日
 * <p>类调用特殊情况：无
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {
	@Override
	public List<UserEntity> findAllUser() {
		return this.baseMapper.selectList(null);
	}

	@Override
	public boolean updateUser(String telephone, String ddUserId) {
		UserEntity user = new UserEntity();
		user.setTelephone(telephone);
		user.setDdUserId(ddUserId);
		return this.baseMapper.updateUser(user);
	}
}